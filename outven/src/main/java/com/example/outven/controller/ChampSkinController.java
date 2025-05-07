package com.example.outven.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.outven.entity.Champ_skin;
import com.example.outven.entity.Champion;
import com.example.outven.entity.Champskinrate;
import com.example.outven.entity.Member;
import com.example.outven.entity.SkinComment;
import com.example.outven.service.ChampSkinService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ChampSkinController {

    @Autowired
    private ChampSkinService champSkinService;

    // ✅ 챔피언 스킨 목록 조회 (페이징 적용)
    @GetMapping("/champ_skin/champSkinList")
    public String champSkinList(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Champion> champList = champSkinService.getChampList(pageable);

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        model.addAttribute("member", member);
        model.addAttribute("champList", champList.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", champList.getTotalPages());

        return "champ_skin/champSkinList";
    }

    // ✅ 챔피언 스킨 상세 조회 및 댓글 목록
    @GetMapping("/champ_skin/champSkinView")
    public String champSkinView(
            @RequestParam("champ_code") int champCode,
            @RequestParam(value = "skin_code", defaultValue = "0") int skinCode,
            @RequestParam(value = "pg", defaultValue = "1") int pg,
            Model model,
            HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        List<Champ_skin> champSkinList = champSkinService.champSkinList(champCode);
        Champ_skin detailView = (skinCode > 0) ? champSkinService.champSkinView(champCode, skinCode) : null;
        boolean commentView = skinCode > 0;

        Pageable pageable = PageRequest.of(pg - 1, 10);
        Page<SkinComment> commentsPage = champSkinService.getSkinComments(skinCode, pageable);

        model.addAttribute("member", member);
        model.addAttribute("champSkinList", champSkinList);
        model.addAttribute("detailView", detailView);
        model.addAttribute("commentsPage", commentsPage);
        model.addAttribute("commentView", commentView);
        model.addAttribute("pg", pg);

        return "champ_skin/champSkinView";
    }

    // ✅ 챔피언 스킨 평점 업데이트
    @GetMapping("/champ_skin/skinRateUpdate")
    public String skinRateUpdate(
            @RequestParam("champ_code") int champCode,
            @RequestParam("skin_code") int skinCode,
            @RequestParam("rate") int rate,
            Model model,
            HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        // 평점 저장
        Champskinrate skinRate = new Champskinrate(champCode, skinCode, member.getMemberId(), rate);
        champSkinService.skinRateWrite(skinRate);

        // 평균 평점 계산 및 업데이트
        double avg = champSkinService.skinRateAvg(champCode, skinCode);
        boolean result = champSkinService.skinRateUpdate(champCode, skinCode, avg);

        model.addAttribute("result", result);
        model.addAttribute("champ_code", champCode);
        model.addAttribute("skin_code", skinCode);

        return "champ_skin/skinRateUpdate";
    }

    // ✅ 챔피언 스킨 댓글 작성
    @PostMapping("/champ_skin/skinComWrite")
    public String skinComWrite(
            @ModelAttribute SkinComment comment,
            @RequestParam("pg") int pg,
            Model model,
            HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        comment.setMember_id(member.getMemberId());
        comment.setNick_name(member.getNickName());
        comment.setComment_logtime(new Date());

        boolean result = champSkinService.skinComWrite(comment);

        model.addAttribute("pg", pg);
        model.addAttribute("result", result);
        model.addAttribute("champ_code", comment.getChampCode());
        model.addAttribute("skin_code", comment.getSkinCode());

        return "champ_skin/skinComWrite";
    }

    // ✅ 챔피언 스킨 답글 작성
    @PostMapping("/champ_skin/recomment")
    public String recomment(
            @ModelAttribute SkinComment comment,
            @RequestParam("pg") int pg,
            Model model,
            HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        comment.setMember_id(member.getMemberId());
        comment.setNick_name(member.getNickName());
        comment.setComment_logtime(new Date());

        boolean result = champSkinService.skinRecomment(comment);

        model.addAttribute("pg", pg);
        model.addAttribute("result", result);
        model.addAttribute("champ_code", comment.getChampCode());
        model.addAttribute("skin_code", comment.getSkinCode());

        return "champ_skin/recomment";
    }

    // ✅ 챔피언 스킨 평점 중복 확인 (AJAX)
    @PostMapping("/checkSkinRate")
    @ResponseBody
    public boolean checkSkinRate(
            @RequestParam("champ_code") int champCode,
            @RequestParam("skin_code") int skinCode,
            @RequestParam("member_id") String memberId) {
        
        return champSkinService.skinRateCheck(champCode, skinCode, memberId);
    }
}
