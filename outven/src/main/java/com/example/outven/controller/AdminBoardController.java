package com.example.outven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.outven.entity.Board;
import com.example.outven.entity.Member;
import com.example.outven.service.AdminService;
import com.example.outven.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class AdminBoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private AdminService adminService;

    // 🔹 [관리자] 리포터 뉴스 게시판 조회 (페이징 적용)
    @GetMapping("/board/admin_reporter_news_BoardList")
    public String reporterNewsBoardList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        String board_category = "리포터 뉴스 게시판";
        String detail_category = request.getParameter("detail_category");

        int pg = request.getParameter("pg") != null ? Integer.parseInt(request.getParameter("pg")) : 1;
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pg - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Board> boardPage;
        if (detail_category != null) {
            boardPage = boardService.getBoardListByCategory(detail_category, pageable);
            model.addAttribute("detail_category", detail_category);
        } else {
            boardPage = boardService.getBoardListByCategory(board_category, pageable);
        }

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("currentPage", pg);
        model.addAttribute("totalPages", boardPage.getTotalPages());

        boolean is_login = session.getAttribute("memId") != null;
        model.addAttribute("is_login", is_login);

        return "/admin/board/admin_reporter_news_BoardList";
    }

    // 🔹 [관리자] 게시글 상세 보기
    @GetMapping("/board/admin_reporter_news_BoardView")
    public String reporterNewsBoardView(Model model, HttpServletRequest request) {
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
            isMemId = board.getMember_id().equals(((Member) session.getAttribute("member")).getMember_id());
        }

        model.addAttribute("pg", pg);
        model.addAttribute("board_num", board_num);
        model.addAttribute("isMemId", isMemId);

        return "/admin/board/admin_reporter_news_BoardView";
    }

    // 🔹 [관리자] 팁 & 공략 게시판 조회 (페이징 적용)
    @GetMapping("/board/admin_tipBoardList")
    public String adminTipBoardList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        String board_category = "팁 & 공략 게시판";
        String detail_category = request.getParameter("detail_category");

        int pg = request.getParameter("pg") != null ? Integer.parseInt(request.getParameter("pg")) : 1;
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pg - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Board> boardPage;
        if (detail_category != null) {
            boardPage = boardService.getBoardListByCategory(detail_category, pageable);
            model.addAttribute("detail_category", detail_category);
        } else {
            boardPage = boardService.getBoardListByCategory(board_category, pageable);
        }

        model.addAttribute("boardPage", boardPage);
        model.addAttribute("currentPage", pg);
        model.addAttribute("totalPages", boardPage.getTotalPages());

        boolean is_login = session.getAttribute("memId") != null;
        model.addAttribute("is_login", is_login);

        return "/admin/board/admin_tipBoardList";
    }

    // 🔹 [관리자] 팁 & 공략 게시판 상세 보기
    @GetMapping("/board/admin_tipBoardView")
    public String adminTipBoardView(Model model, HttpServletRequest request) {
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
            isMemId = board.getMember_id().equals(((Member) session.getAttribute("member")).getMember_id());
        }

        model.addAttribute("pg", pg);
        model.addAttribute("board_num", board_num);
        model.addAttribute("isMemId", isMemId);

        return "/admin/board/admin_tipBoardView";
    }

    // 🔹 [관리자] 게시글 이동
    @PostMapping(value = "/admin/boardMove", consumes = "application/json")
    @ResponseBody
    public boolean adminBoardMove(@RequestBody List<Map<String, String>> selectedBoards) {
        boolean result = true;
        for (Map<String, String> board : selectedBoards) {
            int boardNum = Integer.parseInt(board.get("board_num"));
            String boardCategory = board.get("board_category");
            String detailCategory = board.get("detail_category");

            boolean moveResult = adminService.adminBoardMove(boardNum, boardCategory, detailCategory);
            if (!moveResult) {
                result = false;
            }
        }
        return result;
    }


    // 🔹 [관리자] 게시글 삭제
    @PostMapping(value = "/admin/boardDelete", consumes = "application/json")
    @ResponseBody
    public boolean adminBoardDelete(@RequestBody List<Map<String, String>> selectedBoards) {
        selectedBoards.forEach(board -> boardService.deleteBoard(Integer.parseInt(board.get("board_num"))));
        return true;
    }
}
