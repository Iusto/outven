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
    public String BoardSearch(@RequestParam(required = false) String keyword,
                              @RequestParam(defaultValue = "0") int page,
                              Model model, HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "board_num"));
        Page<Board> boardPage = boardService.searchByTitle(keyword, pageable);

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

        return "/board/boardMainSearchList";
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
            isMemId = board.getMember_id().equals(member.getMember_id());
        }

        model.addAttribute("board", board);
        model.addAttribute("pg", pg);
        model.addAttribute("board_num", board_num);
        model.addAttribute("isMemId", isMemId);

        return "/board/boardMainSearchView";
    }
}        