package com.example.outven.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

@Controller
public class TipBoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/tipBoardList")
    public String tipBoardList(Model model, HttpServletRequest request,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(required = false) String detail_category) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        String board_category = "팁 & 공략 게시판";
        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "board_num"));

        Page<Board> boardPage;
        if (detail_category != null && !detail_category.isEmpty()) {
            boardPage = boardService.getBoardsByCategoryAndDetail(board_category, detail_category, pageable);
            model.addAttribute("detail_category", detail_category);
        } else {
            boardPage = boardService.getBoardListByCategory(board_category, pageable);
        }

        int currentPage = boardPage.getNumber() + 1;
        int totalPages = boardPage.getTotalPages();
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

        return "/board/tipBoardList";
    }

    @GetMapping("/board/tipBoardView")
    public String tipBoardView(Model model, HttpServletRequest request) {
        int board_num = Integer.parseInt(request.getParameter("board_num"));
        int pg = Integer.parseInt(request.getParameter("pg"));

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        boardService.updateHit(board_num);
        Board board = boardService.boardView(board_num);

        boolean isMemId = false;
        Member member = (Member) session.getAttribute("member");
        if (member != null && board.getMember_id().equals(member.getMember_id())) {
            isMemId = true;
        }

        model.addAttribute("board", board);
        model.addAttribute("pg", pg);
        model.addAttribute("board_num", board_num);
        model.addAttribute("isMemId", isMemId);

        return "/board/tipBoardView";
    }

    @GetMapping("/board/tipBoardWriteForm")
    public String tipBoardWriteForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        return "/board/tipBoardWriteForm";
    }

    @PostMapping("/board/tipBoardWrite")
    public String tipBoardWrite(BoardDTO dto, Model model, HttpServletRequest request,
                                 @RequestParam("img") MultipartFile multipartFile) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        dto.setBoard_category("팁 & 공략 게시판");
        dto.setBoard_logtime(new Date());

        if (!multipartFile.isEmpty()) {
            try {
                String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_" + multipartFile.getOriginalFilename();
                File file = new File("static/storage", fileName);
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
                dto.setBoard_img(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean result = boardService.createBoard(dto);
        model.addAttribute("result", result);
        model.addAttribute("pg", 1);
        return "/board/tipBoardWrite";
    }

    @GetMapping("/board/tipBoardModifyForm")
    public String tipBoardModifyForm(@RequestParam int board_num, @RequestParam int pg, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        Board board = boardService.boardView(board_num);
        model.addAttribute("board", board);
        model.addAttribute("pg", pg);
        if (board.getBoard_img() == null) model.addAttribute("noimg", "no");

        return "/board/tipBoardModifyForm";
    }

    @PostMapping("/board/tipBoardModify")
    public String tipBoardModify(BoardDTO dto, Model model, HttpServletRequest request,
                                  @RequestParam(name = "img2", required = false) MultipartFile multipartFile2) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        int pg = Integer.parseInt(request.getParameter("pg"));

        if (multipartFile2 != null && !multipartFile2.isEmpty()) {
            try {
                String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_" + multipartFile2.getOriginalFilename();
                File file = new File("static/storage", fileName);
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                FileCopyUtils.copy(multipartFile2.getInputStream(), new FileOutputStream(file));
                dto.setBoard_img(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Board board = boardService.boardView(dto.getBoard_num());
            dto.setBoard_img(board.getBoard_img());
        }

        boolean result = boardService.updateBoard(dto);
        model.addAttribute("result", result);
        model.addAttribute("pg", pg);
        return "/board/tipBoardModify";
    }

    @GetMapping("/board/tipBoardDelete")
    public String tipBoardDelete(@RequestParam int board_num, @RequestParam int pg, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        boolean result = boardService.deleteBoard(board_num);
        model.addAttribute("result", result);
        model.addAttribute("pg", pg);
        return "/board/tipBoardDelete";
    }
}
