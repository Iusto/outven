package com.example.outven.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.outven.dto.BoardDTO;
import com.example.outven.entity.Board;
import com.example.outven.entity.Board_comment;
import com.example.outven.entity.Boardreport;
import com.example.outven.entity.Member;
import com.example.outven.entity.Recommend;
import com.example.outven.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/board")
public class ReporternewsBoardController {

    @Autowired
    private BoardService boardService;

    // ✅ 게시판 목록 (페이징)
    @GetMapping("/board/reporter_news_BoardList")
    public String reporterNewsBoardList(
            Model model, HttpServletRequest request, Pageable pageable) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        // 카테고리 가져오기
        String board_category = "리포터 뉴스 게시판";
        String detail_category = request.getParameter("detail_category");

        // 페이징 처리된 게시글 조회
        Page<Board> list;
        if (detail_category != null) {
            list = boardService.getBoardsByCategoryAndDetail(board_category, detail_category, pageable);
            model.addAttribute("detail_category", detail_category);
        } else {
            list = boardService.getBoardListByCategory(board_category, pageable);
        }
        model.addAttribute("list", list);

        return "/board/reporter_news_BoardList";
    }

    // ✅ 게시글 상세 조회
    @GetMapping("/board/reporter_news_BoardView")
    public String reporterNewsBoardView(@PathVariable int board_num, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

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
        return "/board/reporter_news_BoardView";
    }

    // ✅ 게시글 작성 폼
    @GetMapping("/board/reporter_news_BoardWriteForm")
    public String reporterNewsBoardWriteForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));
        return "/board/reporter_news_BoardWriteForm";
    }

    // ✅ 게시글 작성
    @PostMapping("/board/reporter_news_BoardWrite")
    public String reporterNewsBoardWrite(
            @ModelAttribute BoardDTO dto, Model model, HttpServletRequest request,
            @RequestParam("img") MultipartFile multipartFile) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        // 카테고리 지정
        String boardCategory = "리포터 뉴스 게시판";
        dto.setBoard_category(boardCategory);

        // 파일 업로드 처리
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dto.setBoard_img(fileName);

        // 저장
        boolean result = boardService.createBoard(dto);
        model.addAttribute("result", result);
        return "/board/reporter_news_BoardWrite";
    }

    // ✅ 게시글 수정 폼
    @GetMapping("/board/reporter_news_BoardModifyForm")
    public String reporterNewsBoardModifyForm(@PathVariable int board_num, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        Board board = boardService.boardView(board_num);
        model.addAttribute("board", board);
        return "/board/reporter_news_BoardModifyForm";
    }

    // ✅ 게시글 수정
    @PostMapping("/board/reporter_news_BoardModify")
    public String reporterNewsBoardModify(
            @ModelAttribute BoardDTO dto, Model model, HttpServletRequest request,
            @RequestParam(name = "img", required = false) MultipartFile multipartFile) {

        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        // 파일 업로드 처리
        String filePath = "static/storage";
        String fileName = dto.getBoard_img();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String formattedDate = dateFormat.format(new Date());
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

        boolean result = boardService.updateBoard(dto);
        model.addAttribute("result", result);
        return "/board/reporter_news_BoardModify";
    }

    // ✅ 게시글 삭제
    @GetMapping("/board/reporter_news_BoardDelete")
    public String reporterNewsBoardDelete(@PathVariable int board_num, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        boolean result = boardService.deleteBoard(board_num);
        model.addAttribute("result", result);
        return "/board/reporter_news_BoardDelete";
    }
}
