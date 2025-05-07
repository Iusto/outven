package com.example.outven.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.outven.dto.PageInfo;
import com.example.outven.entity.Board;
import com.example.outven.entity.Member;
import com.example.outven.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class SearchController {

    @Autowired
    BoardService boardService;
    @Autowired
    ApplicationContext applicationContext;

    @GetMapping("/mainSearch")
    public String BoardSearch(@RequestParam(name = "keyword", required = false) String keyword,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               Model model, HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "boardNum"));

        Page<Board> boardPage = boardService.searchByTitle(keyword, pageable);

        int totalPages = boardPage.getTotalPages();
        int currentPage = boardPage.getNumber(); // 여기서는 0부터 시작 (주의)
        int startPage = Math.max(currentPage - 2, 0);
        int endPage = Math.min(currentPage + 2, totalPages - 1);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        PageInfo pageInfo = new PageInfo(currentPage, totalPages, pageNumbers); // 🔥 추가

        model.addAttribute("page", boardPage);
        model.addAttribute("pageInfo", pageInfo); // 🔥 추가
        model.addAttribute("keyword", keyword);

        return "/board/BoardMainSearchList";
    }


    @GetMapping("/board/boardMainSearchView")
    public String boardMainSearchView(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        int board_num = Integer.parseInt(request.getParameter("board_num"));
        int pg = Integer.parseInt(request.getParameter("pg"));

        boardService.updateHit(board_num);
        Board board = boardService.boardView(board_num);

        boolean isMemId = false;
        if (session.getAttribute("member") != null) {
            Member member = (Member) session.getAttribute("member");
            isMemId = board.getMemberId().equals(member.getMemberId());
        }

        model.addAttribute("board", board);
        model.addAttribute("pg", pg);
        model.addAttribute("board_num", board_num);
        model.addAttribute("isMemId", isMemId);

        return "/board/BoardMainSearchView";
    }
}        