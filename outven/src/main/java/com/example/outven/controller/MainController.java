package com.example.outven.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.outven.dto.BoardDTO;
import com.example.outven.dto.PageDTO;
import com.example.outven.entity.Board;
import com.example.outven.entity.Board_comment;
import com.example.outven.entity.Boardreport;
import com.example.outven.entity.Member;
import com.example.outven.entity.Recommend;
import com.example.outven.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class MainController {

	@Autowired
	BoardService boardService;
	@Autowired
	ApplicationContext applicationContext;
	
	// 메인 홈페이지로 이동
	@GetMapping("/index")
	public String index(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		model.addAttribute("member", session.getAttribute("member"));
		List<Board> recommend_board = boardService.recommendBoardCategoryList(1, 8);	// 추천게시판
		List<Board> art_board = boardService.boardCategoryList("이미지게시판", 1, 4);
		List<Board> patch_board = boardService.boardCategoryList("패치 & 정보 게시판", 1, 10);
		List<Board> tip_board = boardService.boardCategoryList("팁 & 공략 게시판", 1, 10);
		List<Board> position_board = boardService.boardCategoryList("포지션 게시판", 1, 10);
		List<Board> acc_board = boardService.boardCategoryList("사건 & 사고 게시판", 1, 10);
		
		model.addAttribute("recommend_board", recommend_board);
		model.addAttribute("art_board", art_board);
		model.addAttribute("patch_board", patch_board);
		model.addAttribute("tip_board", tip_board);
		model.addAttribute("position_board", position_board);
		model.addAttribute("acc_board", acc_board);
		
		return "main/index";
	}
}
