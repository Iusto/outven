package com.example.outven.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.outven.dto.CommentPageInfo;
import com.example.outven.dto.PageInfo;
import com.example.outven.entity.Board;
import com.example.outven.entity.Board_comment;
import com.example.outven.entity.Member;
import com.example.outven.service.BoardService;
import com.example.outven.service.FileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board") // 🔥 여기 추가: board 전용 경로
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

    private String mapTypeToCategory(String type) {
        return switch (type) {
            case "reporter_news_BoardList" -> "리포터뉴스";
            case "tipBoardList" -> "팁 & 공략 게시판";
            case "patchBoardList" -> "패치 & 정보 게시판";
            case "positionBoardList" -> "포지션 게시판";
            case "recommend_BoardList" -> "추천 게시판";
            case "freeBoardList" -> "자유 게시판";
            case "accidentBoardList" -> "사건 & 사고 게시판";
            case "suggest_report_BoardList" -> "사이트 건의 / 제보";
            case "imageBoardList" -> "팬아트/카툰 게시판";
            default -> throw new IllegalArgumentException("존재하지 않는 게시판 타입입니다: " + type);
        };
    }

    private List<String> getCategoryOptions(String boardCategory) {
        return switch (boardCategory) {
            case "사건 & 사고 게시판" -> List.of("욕설", "잠수", "게임방해", "헬퍼", "오토");
            case "리포터뉴스" -> List.of("국내", "해외", "대회소식");
            case "팁 & 공략 게시판" -> List.of("공략", "팁", "정보");
            case "패치 & 정보 게시판" -> List.of("패치", "공지", "기타");
            case "포지션 게시판" -> List.of("탑", "정글", "미드", "원딜", "서폿");
            case "사이트 건의 / 제보" -> List.of("건의", "제보");
            case "팬아트/카툰 게시판" -> List.of("일반", "습작", "기타");
            default -> null;
        };
    }

    // 🔹 게시판 목록 조회
    @GetMapping("/{type}")
    public String boardListWithType(@PathVariable("type") String type,
                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "detail_category", required = false) String detail_category,
                                    @RequestParam(name = "name", required = false) String name,
                                    @RequestParam(name = "keyword", required = false) String keyword,
                                    Model model, HttpServletRequest request) {

        String boardCategory = mapTypeToCategory(type);

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boardPage;

        if ("추천 게시판".equals(boardCategory)) {
            boardPage = boardService.getRecommendedBoards(10, pageable);
        } else if (keyword != null && name != null) {
            switch (name) {
                case "subject" -> boardPage = boardService.searchByTitleAndCategory(keyword, boardCategory, pageable);
                case "content" -> boardPage = boardService.searchByContentAndCategory(keyword, boardCategory, pageable);
                case "nicname" -> boardPage = boardService.searchByNickNameAndCategory(keyword, boardCategory, pageable);
                case "category" -> boardPage = boardService.searchByMemberIdAndCategory(keyword, boardCategory, pageable);
                case "subjcont" -> boardPage = boardService.searchByTitleOrContentAndCategory(keyword, keyword, boardCategory, pageable);
                default -> boardPage = boardService.getBoardListByCategory(boardCategory, pageable);
            }
        } else if (detail_category != null && !detail_category.isEmpty()) {
            boardPage = boardService.getBoardsByCategoryAndDetail(boardCategory, detail_category, pageable);
            model.addAttribute("detail_category", detail_category);
        } else {
            boardPage = boardService.getBoardListByCategory(boardCategory, pageable);
        }

        List<String> categoryOptions = getCategoryOptions(boardCategory);

        int totalPages = boardPage.getTotalPages();
        int currentPage = boardPage.getNumber();
        int startPage = Math.max(currentPage - 2, 0);
        int endPage = Math.min(currentPage + 2, totalPages - 1);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        PageInfo pageInfo = new PageInfo(currentPage, totalPages, pageNumbers);

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("category", boardCategory);
        model.addAttribute("detail_category", detail_category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("name", name);
        model.addAttribute("categoryOptions", categoryOptions);

        if ("팬아트/카툰 게시판".equals(boardCategory)) {
            return "board/imageBoardList";
        } else {
            return "board/BoardList";
        }
    }

    // 🔹 게시글 상세 보기
    @GetMapping("/view")
    public String boardView(Model model, @RequestParam("board_num") int board_num,
                             @RequestParam(name = "page", defaultValue = "0") int page,
                             @RequestParam(name = "category") String board_category,
                             @RequestParam(name = "comPg", defaultValue = "1") int comPg,
                             HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        boardService.updateHit(board_num);
        Board board = boardService.boardView(board_num);
        model.addAttribute("board", board);

        boolean isMemId = false;
        if (session.getAttribute("member") instanceof Member member) {
            isMemId = board.getMemberId().equals(member.getMemberId());
        }

        Pageable commentPageable = PageRequest.of(comPg - 1, 10, Sort.by(Sort.Direction.DESC, "commentLogtime"));
        Page<Board_comment> commentPage = boardService.getCommentList(board_num, commentPageable);

        List<Integer> commentPageList = new ArrayList<>();
        int totalCommentPages = commentPage.getTotalPages();
        int currentCommentPage = commentPage.getNumber() + 1;
        int commentStartPage = Math.max(currentCommentPage - 2, 1);
        int commentEndPage = Math.min(currentCommentPage + 2, totalCommentPages);

        for (int i = commentStartPage; i <= commentEndPage; i++) {
            commentPageList.add(i);
        }

        CommentPageInfo commentPageInfo = new CommentPageInfo(currentCommentPage, totalCommentPages, commentPageList);

        model.addAttribute("commentList", commentPage.getContent());
        model.addAttribute("commentPageInfo", commentPageInfo);
        model.addAttribute("listExist", !commentPage.isEmpty());
        model.addAttribute("page", page);
        model.addAttribute("board_num", board_num);
        model.addAttribute("isMemId", isMemId);
        model.addAttribute("category", board_category);

        return "board/BoardView";
    }

    // 🔹 게시글 작성 폼
    @GetMapping("/writeForm")
    public String boardWriteForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        return "board/boardWriteForm";
    }

    // 🔹 게시글 작성 처리
    @PostMapping("/write")
    public String boardWrite(@RequestParam("img") MultipartFile multipartFile,
                              @RequestParam String member_id,
                              @RequestParam String nick_name,
                              @RequestParam String detail_category,
                              @RequestParam String board_title,
                              @RequestParam String board_content,
                              @RequestParam String board_category,
                              Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        Board board = new Board();
        board.setBoardCategory(board_category);
        board.setMemberId(member_id);
        board.setNickName(nick_name);
        board.setDetailCategory(detail_category);
        board.setBoardTitle(board_title);
        board.setBoardContent(board_content);
        board.setCreatedAt(LocalDateTime.now());

        String fileName = fileService.storeFile(multipartFile);
        board.setBoardImg(fileName);

        boardService.saveBoard(board);
        return "redirect:/board/" + board_category;
    }

    // 🔹 게시글 삭제
    @GetMapping("/delete")
    public String boardDelete(Model model, @RequestParam int board_num, @RequestParam String category, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        Board board = boardService.boardView(board_num);

        if (member == null || !board.getMemberId().equals(member.getMemberId())) {
            throw new RuntimeException("본인의 글만 삭제할 수 있습니다.");
        }

        boardService.deleteBoard(board_num);
        model.addAttribute("result", true);
        model.addAttribute("category", category);
        model.addAttribute("action", "delete");
        return "board/BoardResult";
    }

    // 🔹 게시글 수정 폼
    @GetMapping("/modifyForm")
    public String boardModifyForm(@RequestParam int board_num, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        Board board = boardService.boardView(board_num);
        if (!board.getMemberId().equals(member.getMemberId())) {
            throw new RuntimeException("본인의 글만 수정할 수 있습니다.");
        }

        model.addAttribute("board", board);
        model.addAttribute("member", member);
        return "board/boardModifyForm";
    }

    // 🔹 게시글 수정 처리
    @PostMapping("/modify")
    public String boardModify(@RequestParam int board_num,
                              @RequestParam String board_title,
                              @RequestParam String board_content,
                              @RequestParam String detail_category,
                              @RequestParam(required = false) MultipartFile img,
                              Model model) {

        Board board = boardService.boardView(board_num);
        board.setBoardTitle(board_title);
        board.setBoardContent(board_content);
        board.setDetailCategory(detail_category);
        board.setUpdatedAt(LocalDateTime.now());

        if (img != null && !img.isEmpty()) {
            String fileName = fileService.storeFile(img);
            board.setBoardImg(fileName);
        }

        boardService.saveBoard(board);
        model.addAttribute("result", true);
        model.addAttribute("board_num", board.getBoardNum());
        model.addAttribute("category", board.getBoardCategory());
        model.addAttribute("action", "modify");
        return "board/BoardResult";
    }
    
    @GetMapping("/popular")
    public ResponseEntity<List<Board>> getPopularBoards() {
        StopWatch sw = new StopWatch();
        sw.start();

        List<Board> boards = boardService.getPopularBoards();

        sw.stop();
        System.out.println("⏱ 응답 시간: " + sw.getTotalTimeMillis() + "ms");
        return ResponseEntity.ok(boards);
    }

}
