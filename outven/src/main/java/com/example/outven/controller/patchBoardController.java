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
import com.example.outven.entity.Board_comment;
import com.example.outven.entity.Member;
import com.example.outven.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PatchBoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/patchBoardList")
    public String patchBoardList(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(required = false) String detail_category,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String keyword,
                                  HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        String board_category = "패치 & 정보 게시판";
        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "board_num"));

        Page<Board> boardPage;

        if (keyword != null && name != null && !keyword.isEmpty() && !name.isEmpty()) {
            switch (name) {
                case "subject" -> boardPage = boardService.searchByTitleAndCategory(keyword, board_category, pageable);
                case "content" -> boardPage = boardService.searchByContentAndCategory(keyword, board_category, pageable);
                case "nicname" -> boardPage = boardService.searchByNickNameAndCategory(keyword, board_category, pageable);
                case "category" -> boardPage = boardService.searchByMemberIdAndCategory(keyword, board_category, pageable);
                case "subjcont" -> boardPage = boardService.searchByTitleOrContentAndCategory(keyword, keyword, board_category, pageable);
                default -> boardPage = boardService.getBoardListByCategory(board_category, pageable);
            }
            model.addAttribute("name", name);
            model.addAttribute("keyword", keyword);
        }
        else if (detail_category != null && !detail_category.isEmpty()) {
            boardPage = boardService.getBoardsByCategoryAndDetail(board_category, detail_category, pageable);
            model.addAttribute("detail_category", detail_category);
        }
        else {
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

        return "/board/patchBoardList";
    }

    @GetMapping("/board/patchBoardView")
    public String patchBoardView(Model model,
                                  @RequestParam int board_num,
                                  @RequestParam(defaultValue = "0") int comPg,
                                  HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        boardService.updateHit(board_num);
        Board board = boardService.boardView(board_num);

        boolean isMemId = false;
        if (session.getAttribute("member") != null) {
            Member member = (Member) session.getAttribute("member");
            isMemId = board.getMember_id().equals(member.getMember_id());
        }

        model.addAttribute("board", board);
        model.addAttribute("board_num", board_num);
        model.addAttribute("isMemId", isMemId);

        Pageable pageable = PageRequest.of(comPg, 10, Sort.by(Sort.Direction.ASC, "com_num"));
        Page<Board_comment> commentPage = boardService.getCommentList(board_num, pageable);

        int totalPages = commentPage.getTotalPages();
        int currentPage = commentPage.getNumber() + 1;
        int startPage = Math.max(currentPage - 2, 1);
        int endPage = Math.min(currentPage + 2, totalPages);

        List<Integer> pageList = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageList.add(i);
        }

        model.addAttribute("commentPage", commentPage);
        model.addAttribute("comPg", comPg);
        model.addAttribute("pageList", pageList);
        model.addAttribute("previousPage", currentPage > 1 ? currentPage - 1 : null);
        model.addAttribute("nextPage", currentPage < totalPages ? currentPage + 1 : null);
        model.addAttribute("listExist", !commentPage.isEmpty());

        return "/board/patchBoardView";
    }

    @GetMapping("/board/patchBoardWriteForm")
    public String patchBoardWriteForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        return "/board/patchBoardWriteForm";
    }

    @PostMapping("/board/patchBoardWrite")
    public String patchBoardWrite(BoardDTO dto, Model model, HttpServletRequest request,
                                   @RequestParam("img") MultipartFile multipartFile) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        dto.setBoard_category("패치 & 정보 게시판");
        dto.setBoard_logtime(new Date());
        dto.setMember_id(request.getParameter("member_id"));
        dto.setNick_name(request.getParameter("nick_name"));
        dto.setDetail_category(request.getParameter("detail_category"));
        dto.setBoard_title(request.getParameter("board_title"));
        dto.setBoard_content(request.getParameter("board_content"));

        String filePath = "static/storage";
        String fileName = "";

        if (!multipartFile.isEmpty()) {
            try {
                String formattedDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                fileName = formattedDate + "_" + multipartFile.getOriginalFilename();
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
        return "/board/patchBoardWrite";
    }
    
    @GetMapping("/board/patchBoardModifyForm")
    public String patchBoardModifyForm(BoardDTO dto, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        int pg = Integer.parseInt(request.getParameter("pg"));
        int board_num = Integer.parseInt(request.getParameter("board_num"));
        Board board = boardService.boardView(board_num);
        String detail_category = board.getDetail_category();

        if (board.getBoard_img() == null) {
            model.addAttribute("noimg", "no");
        }

        model.addAttribute("board", board);
        model.addAttribute("pg", pg);
        model.addAttribute("detail_category", detail_category);

        return "/board/patchBoardModifyForm";
    }

    @PostMapping("/board/patchBoardModify")
    public String patchBoardModify(BoardDTO dto, Model model,
                                    HttpServletRequest request,
                                    @RequestParam(name = "img2", required = false) MultipartFile multipartFile2) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        int pg = Integer.parseInt(request.getParameter("pg"));
        int board_num = Integer.parseInt(request.getParameter("board_num"));

        if (multipartFile2 == null || multipartFile2.isEmpty()) {
            Board board = boardService.boardView(board_num);
            dto.setBoard_img(board.getBoard_img());
        } else {
            try {
                String formattedDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = formattedDate + "_" + multipartFile2.getOriginalFilename();
                File file = new File("static/storage", fileName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                FileCopyUtils.copy(multipartFile2.getInputStream(), new FileOutputStream(file));
                dto.setBoard_img(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean result = boardService.updateBoard(dto);
        model.addAttribute("result", result);
        model.addAttribute("board_num", board_num);
        model.addAttribute("pg", pg);
        return "/board/patchBoardModify";
    }

    @GetMapping("/board/patchBoardDelete")
    public String patchBoardDelete(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        int board_num = Integer.parseInt(request.getParameter("board_num"));
        int pg = Integer.parseInt(request.getParameter("pg"));

        boolean result = boardService.deleteBoard(board_num);
        model.addAttribute("pg", pg);
        model.addAttribute("result", result);
        return "/board/patchBoardDelete";
    }
}
