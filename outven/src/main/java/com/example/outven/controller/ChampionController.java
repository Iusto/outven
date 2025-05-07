package com.example.outven.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.outven.entity.Champion;
import com.example.outven.entity.Champion_comment;
import com.example.outven.entity.Championrate;
import com.example.outven.entity.Member;
import com.example.outven.service.ChampionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ChampionController {

    @Autowired
    private ChampionService championService;

    // 챔피언 리스트 조회
    @GetMapping("/champ_board/championList")
    public String championList(Model model, HttpServletRequest request,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("champCode").ascending());

        // `List<Champion>`을 가져오기 위해 변환
        List<Champion> champList = championService.getChampListAsList(pageable);

        model.addAttribute("champList", champList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", championService.champList(pageable).getTotalPages());

        return "champ_board/championList";
    }


    // 챔피언 상세보기 + 댓글 목록 (JPA Pageable 적용)
    @GetMapping("/champ_board/championView")
    public String championView(
            @RequestParam("champ_code") int champCode,
            @RequestParam(value = "pg", defaultValue = "1") int pg,
            Model model,
            HttpServletRequest request
    ) {
        // 챔피언 상세 정보 조회
        Champion champion = championService.championView(champCode);

        // 댓글 페이징 처리 (10개씩)
        Pageable pageable = PageRequest.of(pg - 1, 10);
        Page<Champion_comment> commentsPage = championService.getCommentsByChampion(champCode, pageable);

        // 세션 공유
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        // 모델에 데이터 추가
        model.addAttribute("champion", champion);
        model.addAttribute("commentsPage", commentsPage);
        model.addAttribute("pg", pg);

        return "champ_board/championView";
    }

    // 챔피언 평점 업데이트
    @GetMapping("/champ_board/championRateUpdate")
    public String championRateUpdate(
            @RequestParam("champ_code") int champCode,
            @RequestParam("rate") int rate,
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        // 평점 저장
        Championrate championRate = new Championrate(champCode, rate, member.getMemberId());
        championService.champRateWrite(championRate);

        // 평점 평균 계산 및 업데이트
        double avg = championService.champRateAvg(champCode);
        avg = Double.parseDouble(String.format("%.2f", avg));
        boolean result = championService.champRateUpdate(champCode, avg);

        model.addAttribute("result", result);
        model.addAttribute("champ_code", champCode);
        return "champ_board/championRateUpdate";
    }

    // 댓글 작성
    @PostMapping("/champ_board/commentWrite")
    public String commentWrite(
            @RequestParam("pg") int pg,
            @RequestParam("champ_code") int champCode,
            @ModelAttribute Champion_comment comment,
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        // 댓글 정보 설정
        comment.setMember_id(member.getMemberId());
        comment.setNick_name(member.getNickName());
        comment.setComment_logtime(new Date());

        boolean result = championService.commentWrite(comment);

        model.addAttribute("result", result);
        model.addAttribute("champ_code", champCode);
        return "champ_board/commentWrite";
    }

    // 답글 작성
    @PostMapping("/champ_board/recomment")
    public String recomment(
            @RequestParam("pg") int pg,
            @ModelAttribute Champion_comment comment,
            HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        // 답글 정보 설정
        comment.setMember_id(member.getMemberId());
        comment.setNick_name(member.getNickName());
        comment.setComment_logtime(new Date());

        boolean result = championService.recomment(comment);

        model.addAttribute("pg", pg);
        model.addAttribute("result", result);
        model.addAttribute("champ_code", comment.getChampCode());
        return "champ_board/recomment";
    }

    // 중복 평점 확인 (AJAX 요청)
    @PostMapping("/checkRate")
    @ResponseBody
    public boolean checkRate(
            @RequestParam("champ_code") int champCode,
            @RequestParam("member_id") String memberId
    ) {
        return championService.champRateCheck(champCode, memberId);
    }
}
