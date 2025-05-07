package com.example.outven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.outven.entity.Board;
import com.example.outven.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private BoardService boardService;

    // 메인 홈페이지로 이동
    @GetMapping({"/", "/index"})
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        // 최신 게시글 8개씩 가져오기
        Pageable pageable8 = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "createdAt"));
        Pageable pageable10 = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Pageable pageable4 = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 추천 게시판 (추천수 5 이상)
        Page<Board> recommendBoard = boardService.getRecommendedBoards(5, pageable8);
        // 이미지 게시판 (최신순 4개)
        Page<Board> artBoard = boardService.getBoardListByCategory("이미지게시판", pageable4);
        // 패치 & 정보 게시판 (최신순 10개)
        Page<Board> patchBoard = boardService.getBoardListByCategory("패치 & 정보 게시판", pageable10);
        // 팁 & 공략 게시판 (최신순 10개)
        Page<Board> tipBoard = boardService.getBoardListByCategory("팁 & 공략 게시판", pageable10);
        // 포지션 게시판 (최신순 10개)
        Page<Board> positionBoard = boardService.getBoardListByCategory("포지션 게시판", pageable10);
        // 사건 & 사고 게시판 (최신순 10개)
        Page<Board> accBoard = boardService.getBoardListByCategory("사건 & 사고 게시판", pageable10);

        // 데이터 공유
        model.addAttribute("recommend_board", recommendBoard.getContent());
        model.addAttribute("art_board", artBoard.getContent());
        model.addAttribute("patch_board", patchBoard.getContent());
        model.addAttribute("tip_board", tipBoard.getContent());
        model.addAttribute("position_board", positionBoard.getContent());
        model.addAttribute("acc_board", accBoard.getContent());

        return "main/index";
    }
}
