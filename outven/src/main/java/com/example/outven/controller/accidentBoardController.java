package com.example.outven.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.outven.entity.Board;
import com.example.outven.entity.Board_comment;
import com.example.outven.entity.Member;
import com.example.outven.service.BoardService;
import com.example.outven.service.FileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccidentBoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

    // 🔹 사건사고 게시판 목록 조회 (검색 포함, 페이징 적용)
    @GetMapping("/board/accidentBoardList")
    public String accidentBoardList(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(required = false) String detail_category,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String keyword,
                                    HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        String board_category = "사건 & 사고 게시판";
        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "board_num"));

        Page<Board> boardPage;

        if (keyword != null && name != null) {
            switch (name) {
                case "subject":
                    boardPage = boardService.searchByTitleAndCategory(keyword, board_category, pageable);
                    break;
                case "content":
                    boardPage = boardService.searchByContentAndCategory(keyword, board_category, pageable);
                    break;
                case "nicname":
                    boardPage = boardService.searchByNickNameAndCategory(keyword, board_category, pageable);
                    break;
                case "category":
                    boardPage = boardService.searchByMemberIdAndCategory(keyword, board_category, pageable);
                    break;
                case "subjcont":
                    boardPage = boardService.searchByTitleOrContentAndCategory(keyword, keyword, board_category, pageable);
                    break;
                default:
                    boardPage = boardService.getBoardListByCategory(board_category, pageable);
            }
        } else if (detail_category != null && !detail_category.isEmpty()) {
            boardPage = boardService.getBoardsByCategoryAndDetail(board_category, detail_category, pageable);
            model.addAttribute("detail_category", detail_category);
        } else {
            boardPage = boardService.getBoardListByCategory(board_category, pageable);
        }

        int totalPages = boardPage.getTotalPages();
        int currentPage = boardPage.getNumber() + 1;
        int startPage = Math.max(currentPage - 2, 1);
        int endPage = Math.min(currentPage + 2, totalPages);

        List<Integer> pageList = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageList.add(i);
        }

        model.addAttribute("page", boardPage);
        model.addAttribute("pageList", pageList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("previousPage", currentPage > 1 ? currentPage - 1 : null);
        model.addAttribute("nextPage", currentPage < totalPages ? currentPage + 1 : null);
        model.addAttribute("keyword", keyword);
        model.addAttribute("name", name);

        return "/board/accidentBoardList";
    }

    // 🔹 게시글 상세 보기
    @GetMapping("/board/accidentBoardView")
    public String accidentBoardView(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        int board_num = Integer.parseInt(request.getParameter("board_num"));
        int pg = Integer.parseInt(request.getParameter("pg"));

        // 조회수 증가
        boardService.updateHit(board_num);

        // 게시글 상세 조회
        Board board = boardService.boardView(board_num);
        model.addAttribute("board", board);

        // 로그인한 사용자와 글 작성자 비교
        boolean isMemId = false;
        if (session.getAttribute("member") != null) {
            Member member = (Member) session.getAttribute("member");
            isMemId = board.getMember_id().equals(member.getMember_id());
        }

        // 댓글 페이징 처리
        int comPg = request.getParameter("comPg") != null ? Integer.parseInt(request.getParameter("comPg")) : 1;
        Pageable commentPageable = PageRequest.of(comPg - 1, 10, Sort.by(Sort.Direction.DESC, "comment_logtime"));
        Page<Board_comment> commentPage = boardService.getCommentList(board_num, commentPageable);

        model.addAttribute("commentList", commentPage.getContent());
        model.addAttribute("listExist", !commentPage.isEmpty());
        model.addAttribute("pg", pg);
        model.addAttribute("board_num", board_num);
        model.addAttribute("isMemId", isMemId);
        model.addAttribute("comPg", comPg);
        model.addAttribute("totalPages", commentPage.getTotalPages());

        return "/board/accidentBoardView";
    }

    // 🔹 게시글 작성 폼
    @GetMapping("/board/accidentBoardWriteForm")
    public String accidentBoardWriteForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        return "/board/accidentBoardWriteForm";
    }

    // 🔹 게시글 작성
    @PostMapping("/board/accidentBoardWrite")
    public String accidentBoardWrite(
        @RequestParam("img") MultipartFile multipartFile,
        @RequestParam String member_id,
        @RequestParam String nick_name,
        @RequestParam String detail_category,
        @RequestParam String board_title,
        @RequestParam String board_content,
        Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        Board board = new Board();
        board.setBoard_category("사건 & 사고 게시판");
        board.setMember_id(member_id);
        board.setNick_name(nick_name);
        board.setDetail_category(detail_category);
        board.setBoard_title(board_title);
        board.setBoard_content(board_content);
        board.setBoard_logtime(new Date());

        // 파일 저장 처리
        String fileName = fileService.storeFile(multipartFile);
        board.setBoard_img(fileName);

        boardService.saveBoard(board);
        model.addAttribute("pg", 1);
        return "/board/accidentBoardWrite";
    }

    // 🔹 게시글 삭제
    @GetMapping("/board/accidentBoardDelete")
    public String accidentBoardDelete(Model model, @RequestParam int board_num) {
        boardService.deleteBoard(board_num);
        model.addAttribute("result", true);
        return "/board/accidentBoardDelete";
    }
}
