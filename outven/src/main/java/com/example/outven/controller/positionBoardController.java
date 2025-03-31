package com.example.outven.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.outven.dto.BoardDTO;
import com.example.outven.entity.Board;
import com.example.outven.entity.Member;
import com.example.outven.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PositionBoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/positionBoardList")
    public String positionBoardList(Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(required = false) String detail_category,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String name,
                                     HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        String boardCategory = "포지션 게시판";
        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "board_num"));
        Page<Board> boardPage;

        if (keyword != null && name != null) {
            switch (name) {
                case "subject":
                    boardPage = boardService.searchByTitleAndCategory(keyword, boardCategory, pageable);
                    break;
                case "content":
                    boardPage = boardService.searchByContentAndCategory(keyword, boardCategory, pageable);
                    break;
                case "nicname":
                    boardPage = boardService.searchByNickNameAndCategory(keyword, boardCategory, pageable);
                    break;
                case "category":
                    boardPage = boardService.searchByMemberIdAndCategory(keyword, boardCategory, pageable);
                    break;
                case "subjcont":
                    boardPage = boardService.searchByTitleOrContentAndCategory(keyword, keyword, boardCategory, pageable);
                    break;
                default:
                    boardPage = boardService.getBoardListByCategory(boardCategory, pageable);
            }
        } else if (detail_category != null && !detail_category.isEmpty()) {
            boardPage = boardService.getBoardsByCategoryAndDetail(boardCategory, detail_category, pageable);
            model.addAttribute("detail_category", detail_category);
        } else {
            boardPage = boardService.getBoardListByCategory(boardCategory, pageable);
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

        return "/board/positionBoardList";
    }

    @GetMapping("/board/positionBoardView")
    public String positionBoardView(Model model, HttpServletRequest request) {
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

        return "/board/positionBoardView";
    }

    @GetMapping("/board/positionBoardWriteForm")
    public String positionBoardWriteForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        return "/board/positionBoardWriteForm";
    }

    @PostMapping("/board/positionBoardWrite")
    public String positionBoardWrite(BoardDTO dto, Model model, HttpServletRequest request,
                                     @RequestParam("img") MultipartFile multipartFile) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        dto.setBoard_category("포지션 게시판");
        dto.setBoard_logtime(new Date());

        String filePath = "static/storage";
        String fileName = "";

        if (!multipartFile.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                fileName = dateFormat.format(new Date()) + "_" + multipartFile.getOriginalFilename();
                File file = new File(filePath, fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dto.setBoard_img(fileName);
        boolean result = boardService.createBoard(dto);

        model.addAttribute("result", result);
        model.addAttribute("pg", 1);
        return "/board/positionBoardWrite";
    }

    @GetMapping("/board/positionBoardModifyForm")
    public String positionBoardModifyForm(BoardDTO dto, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        int pg = Integer.parseInt(request.getParameter("pg"));
        int board_num = Integer.parseInt(request.getParameter("board_num"));
        Board board = boardService.boardView(board_num);

        if (board.getBoard_img() == null) {
            model.addAttribute("noimg", "no");
        }

        model.addAttribute("board", board);
        model.addAttribute("pg", pg);

        return "/board/positionBoardModifyForm";
    }

    @PostMapping("/board/positionBoardModify")
    public String positionBoardModify(BoardDTO dto, Model model, HttpServletRequest request,
                                      @RequestParam(name = "img2", required = false) MultipartFile multipartFile2) {
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
                e.printStackTrace();
            }
        } else {
            Board board = boardService.boardView(dto.getBoard_num());
            dto.setBoard_img(board.getBoard_img());
        }

        boolean result = boardService.updateBoard(dto);
        model.addAttribute("result", result);
        model.addAttribute("pg", pg);
        return "/board/positionBoardModify";
    }

    @GetMapping("/board/positionBoardDelete")
    public String positionBoardDelete(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        int board_num = Integer.parseInt(request.getParameter("board_num"));
        int pg = Integer.parseInt(request.getParameter("pg"));

        boolean result = boardService.deleteBoard(board_num);
        model.addAttribute("pg", pg);
        model.addAttribute("result", result);
        return "/board/positionBoardDelete";
    }
} 
