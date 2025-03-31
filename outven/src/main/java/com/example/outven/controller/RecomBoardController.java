package com.example.outven.controller;

import com.example.outven.entity.Board;
import com.example.outven.entity.Board_comment;
import com.example.outven.entity.Member;
import com.example.outven.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecomBoardController {

    @Autowired
    BoardService boardService;
    @Autowired
    ApplicationContext applicationContext;

    @GetMapping("/board/recommend_BoardList")
    public String recommendBoardList(Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(required = false) String name,
                                     @RequestParam(required = false) String keyword,
                                     HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "board_num"));
        Page<Board> boardPage;

        if (keyword != null && name != null) {
            switch (name) {
                case "subject":
                    boardPage = boardService.searchByTitleAndCategory(keyword, "추천 게시판", pageable);
                    break;
                case "content":
                    boardPage = boardService.searchByContentAndCategory(keyword, "추천 게시판", pageable);
                    break;
                case "nicname":
                    boardPage = boardService.searchByNickNameAndCategory(keyword, "추천 게시판", pageable);
                    break;
                case "category":
                    boardPage = boardService.searchByMemberIdAndCategory(keyword, "추천 게시판", pageable);
                    break;
                case "subjcont":
                    boardPage = boardService.searchByTitleOrContentAndCategory(keyword, keyword, "추천 게시판", pageable);
                    break;
                default:
                    boardPage = boardService.getBoardsByRecommendGreaterThanEqual(5, pageable);
            }
        } else {
            boardPage = boardService.getBoardsByRecommendGreaterThanEqual(5, pageable);
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
        model.addAttribute("is_login", session.getAttribute("memId") != null);
        model.addAttribute("keyword", keyword);
        model.addAttribute("name", name);

        return "/board/recommend_BoardList";
    }

    @GetMapping("/board/recommend_BoardView")
    public String recommendBoardView(@RequestParam("board_num") int board_num,
                                     @RequestParam(value = "pg", defaultValue = "1") int pg,
                                     @RequestParam(value = "comPg", defaultValue = "0") int comPg,
                                     Model model,
                                     HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        boardService.updateHit(board_num);
        Board board = boardService.boardView(board_num);

        boolean isMemId = false;
        Member member = (Member) session.getAttribute("member");
        if (member != null && member.getMember_id().equals(board.getMember_id())) {
            isMemId = true;
        }

        Pageable commentPageable = PageRequest.of(comPg, 10, Sort.by(Sort.Direction.DESC, "board_comment_num"));
        Page<Board_comment> commentPage = boardService.getCommentList(board_num, commentPageable);

        int totalPages = commentPage.getTotalPages();
        int currentPage = commentPage.getNumber() + 1;
        int startPage = Math.max(currentPage - 2, 1);
        int endPage = Math.min(currentPage + 2, totalPages);

        List<Integer> pageList = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageList.add(i);
        }

        model.addAttribute("board", board);
        model.addAttribute("pg", pg);
        model.addAttribute("isMemId", isMemId);
        model.addAttribute("commentPage", commentPage);
        model.addAttribute("comPg", currentPage);
        model.addAttribute("pageList", pageList);
        model.addAttribute("previousPage", currentPage > 1 ? currentPage - 1 : null);
        model.addAttribute("nextPage", currentPage < totalPages ? currentPage + 1 : null);

        return "/board/recommend_BoardView";
    }
}
