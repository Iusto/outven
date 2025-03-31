package com.example.outven.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.outven.dto.BoardDTO;
import com.example.outven.entity.Board;
import com.example.outven.entity.Board_comment;
import com.example.outven.entity.Member;
import com.example.outven.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class FreeBoardController {

	private static final Logger logger = LoggerFactory.getLogger(FreeBoardController.class);

    @Autowired
    private BoardService boardService;

    // ✅ 자유 게시판 목록 + 검색
    @GetMapping("/board/freeBoardList")
    public String freeBoardList(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) String detail_category,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String keyword,
                                HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        String board_category = "자유 게시판";
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

        logger.info("게시글 조회 완료: 현재 페이지 = {}, 총 페이지 = {}", currentPage, totalPages);
        return "/board/freeBoardList";
    }
    // ✅ 게시글 작성 Form 페이지
    @GetMapping("/board/freeBoardWriteForm")
    public String freeBoardWriteForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        return "/board/freeBoardWriteForm";
    }

    // ✅ 게시글 작성
    @PostMapping("/board/freeBoardWrite")
    public String freeBoardWrite(BoardDTO dto, Model model, HttpServletRequest request,
                                 @RequestParam("img") MultipartFile multipartFile) {
        logger.info("게시글 작성 요청: 제목 = {}", dto.getBoard_title());

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        model.addAttribute("pg", session.getAttribute("pg"));

        dto.setBoard_category("자유 게시판");
        dto.setBoard_logtime(new Date());

        // ✅ 파일 업로드 처리
        String filePath = "static/storage";
        String fileName = "";

        if (!multipartFile.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String formattedDate = dateFormat.format(new Date());
                fileName = formattedDate + "_" + multipartFile.getOriginalFilename();
                File file = new File(filePath, fileName);

                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));

            } catch (FileNotFoundException e) {
                logger.error("파일 저장 실패: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("파일 처리 중 오류 발생: {}", e.getMessage());
            }
        }

        dto.setBoard_img(fileName);

        boolean result = boardService.createBoard(dto);
        logger.info("게시글 저장 결과: {}", result);

        model.addAttribute("result", result);
        model.addAttribute("pg", 1);
        return "/board/freeBoardWrite";
    }

    // ✅ 게시글 수정 Form 페이지
    @GetMapping("/board/freeBoardModifyForm")
    public String freeBoardModifyForm(BoardDTO dto, Model model, HttpServletRequest request) {
        logger.info("게시글 수정 페이지 요청");

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        int pg = Integer.parseInt(request.getParameter("pg"));
        int board_num = Integer.parseInt(request.getParameter("board_num"));
        Board board = boardService.boardView(board_num);

        model.addAttribute("board", board);
        model.addAttribute("pg", pg);

        return "/board/freeBoardModifyForm";
    }
    
    @GetMapping("/board/freeBoardView")
	public String freeBoardView(Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        
        int board_num = Integer.parseInt(request.getParameter("board_num"));

        // 조회수 증가
        boardService.updateHit(board_num);
        Board board = boardService.boardView(board_num);

        // 작성자 확인
        boolean isMemId = false;
        if (session.getAttribute("member") != null) {
            Member member = (Member) session.getAttribute("member");
            isMemId = board.getMember_id().equals(member.getMember_id());
        }

        model.addAttribute("board", board);
        model.addAttribute("isMemId", isMemId);
        return "/board/freeBoardView";
    }
    

    // ✅ 게시글 수정
    @PostMapping("/board/freeBoardModify")
    public String freeBoardModify(BoardDTO dto, Model model, HttpServletRequest request,
                                  @RequestParam(name = "img2", required = false) MultipartFile multipartFile2) {
        logger.info("게시글 수정 요청: 번호 = {}", dto.getBoard_num());

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        int pg = Integer.parseInt(request.getParameter("pg"));

        if (multipartFile2 != null && !multipartFile2.isEmpty()) {
            String filePath = "static/storage";
            String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_" + multipartFile2.getOriginalFilename();

            try {
                File file = new File(filePath, fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileCopyUtils.copy(multipartFile2.getInputStream(), new FileOutputStream(file));
                dto.setBoard_img(fileName);
            } catch (Exception e) {
                logger.error("파일 처리 중 오류 발생: {}", e.getMessage());
            }
        } else {
            Board board = boardService.boardView(dto.getBoard_num());
            dto.setBoard_img(board.getBoard_img());
        }

        boolean result = boardService.updateBoard(dto);
        logger.info("게시글 수정 결과: {}", result);

        model.addAttribute("result", result);
        model.addAttribute("pg", pg);

        return "/board/freeBoardModify";
    }

    // ✅ 게시글 삭제
    @GetMapping("/board/freeBoardDelete")
    public String freeBoardDelete(Model model, HttpServletRequest request) {
        logger.info("게시글 삭제 요청");

        int board_num = Integer.parseInt(request.getParameter("board_num"));
        int pg = Integer.parseInt(request.getParameter("pg"));

        boolean result = boardService.deleteBoard(board_num);
        logger.info("게시글 삭제 완료: 게시글 번호 = {}, 결과 = {}", board_num, result);

        model.addAttribute("pg", pg);
        model.addAttribute("result", result);
        return "/board/freeBoardDelete";
    }
}
