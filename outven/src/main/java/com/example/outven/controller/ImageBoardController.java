package com.example.outven.controller;

import com.example.outven.dto.BoardDTO;
import com.example.outven.entity.Board;
import com.example.outven.entity.Board_comment;
import com.example.outven.entity.Member;
import com.example.outven.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ImageBoardController {

    private static final Logger logger = LoggerFactory.getLogger(ImageBoardController.class);

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/imageBoardList")
    public String imageBoardList(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(required = false) String detail_category,
                                  HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        String board_category = "이미지게시판";
        Pageable pageable = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "board_num"));

        Page<Board> boardPage;
        if (detail_category != null && !detail_category.isEmpty()) {
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

        return "/board/imageBoardList";
    }

    @GetMapping("/board/imageBoardView")
    public String imageBoardView(@RequestParam("board_num") int board_num,
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
        if (member != null && member.getMemberId().equals(board.getMemberId())) {
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

        return "/board/imageBoardView";
    }

    @GetMapping("/board/imageBoardWriteForm")
    public String imageBoardWriteForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        return "/board/imageBoardWriteForm";
    }

    @PostMapping("/board/imageBoardWrite")
    public String imageBoardWrite(BoardDTO dto,
                                   @RequestParam("img") MultipartFile multipartFile,
                                   Model model,
                                   HttpServletRequest request) {
        logger.info("이미지 게시판 글 작성 요청: 제목 = {}", dto.getBoard_title());

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        dto.setBoard_category("이미지게시판");
        dto.setCreatedAt(LocalDateTime.now());

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
                logger.error("파일 저장 실패: {}", e.getMessage());
            }
        }

        dto.setBoard_img(fileName);
        boolean result = boardService.createBoard(dto);
        model.addAttribute("result", result);
        model.addAttribute("pg", 1);
        return "/board/imageBoardWrite";
    }

    @GetMapping("/board/imageBoardModifyForm")
    public String imageBoardModifyForm(@RequestParam("board_num") int board_num,
                                       @RequestParam("pg") int pg,
                                       Model model,
                                       HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        Board board = boardService.boardView(board_num);
        model.addAttribute("board", board);
        model.addAttribute("pg", pg);

        return "/board/imageBoardModifyForm";
    }

    @PostMapping("/board/imageBoardModify")
    public String imageBoardModify(BoardDTO dto,
                                    @RequestParam(name = "img2", required = false) MultipartFile multipartFile2,
                                    @RequestParam("pg") int pg,
                                    Model model,
                                    HttpServletRequest request) {
        logger.info("이미지 게시판 수정 요청: 번호 = {}", dto.getBoard_num());

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

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
                logger.error("파일 업로드 실패: {}", e.getMessage());
            }
        } else {
            Board board = boardService.boardView(dto.getBoard_num());
            dto.setBoard_img(board.getBoardImg());
        }

        boolean result = boardService.updateBoard(dto);
        model.addAttribute("result", result);
        model.addAttribute("pg", pg);
        return "/board/imageBoardModify";
    }

    @GetMapping("/board/imageBoardDelete")
    public String imageBoardDelete(@RequestParam("board_num") int board_num,
                                   @RequestParam("pg") int pg,
                                   Model model) {
        boolean result = boardService.deleteBoard(board_num);
        model.addAttribute("result", result);
        model.addAttribute("pg", pg);
        return "/board/imageBoardDelete";
    }
}
