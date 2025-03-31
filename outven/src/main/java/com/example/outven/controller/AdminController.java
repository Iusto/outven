package com.example.outven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.outven.entity.Member;
import com.example.outven.service.AdminService;
import com.example.outven.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BoardService boardService;

    // 🔹 관리자 메인 페이지 이동
    @GetMapping("/admin/index")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("member", session.getAttribute("member"));

        // 여러 게시판 데이터 불러오기
        model.addAttribute("recommend_board", boardService.getBoardListByCategory("추천 게시판", PageRequest.of(0, 8)).getContent());
        model.addAttribute("art_board", boardService.getBoardListByCategory("이미지게시판", PageRequest.of(0, 4)).getContent());
        model.addAttribute("patch_board", boardService.getBoardListByCategory("패치 & 정보 게시판", PageRequest.of(0, 10)).getContent());
        model.addAttribute("tip_board", boardService.getBoardListByCategory("팁 & 공략 게시판", PageRequest.of(0, 10)).getContent());
        model.addAttribute("position_board", boardService.getBoardListByCategory("포지션 게시판", PageRequest.of(0, 10)).getContent());
        model.addAttribute("acc_board", boardService.getBoardListByCategory("사건 & 사고 게시판", PageRequest.of(0, 10)).getContent());

        return "/admin/main/adminindex";
    }

    // 🔹 관리자 로그인 페이지 이동
    @GetMapping("/adminLoginForm")
    public String adminForm(HttpServletRequest request, Model model) {
        model.addAttribute("member", request.getSession().getAttribute("member"));
        return "admin/member/adminLoginForm";
    }

    // 🔹 관리자 로그인
    @PostMapping("/admin/login")
    public String adminLogin(HttpServletRequest request, Model model) {
        String id = request.getParameter("username");
        String pw = request.getParameter("password");

        Member member = adminService.Adminlogin(id, pw);

        if (member != null) {
            request.getSession().setAttribute("member", member);
            model.addAttribute("result", true);
        } else {
            model.addAttribute("result", false);
        }

        return "admin/member/adminLogin";
    }

    // 🔹 관리자 회원 관리 (페이징 적용)
    @GetMapping("/admin/memberCheck")
    public String adminMemberCheck(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = request.getParameter("role");

        int pg = request.getParameter("pg") != null ? Integer.parseInt(request.getParameter("pg")) : 1;
        int pageSize = 30;
        Pageable pageable = PageRequest.of(pg - 1, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Member> memberPage = adminService.getMembersByRole(role, pageable);

        model.addAttribute("memberPage", memberPage);
        model.addAttribute("currentPage", pg);
        model.addAttribute("totalPages", memberPage.getTotalPages());
        model.addAttribute("role", role);
        model.addAttribute("member", session.getAttribute("member"));

        return "/admin/member/adminMemberCheck";
    }

    // 🔹 [관리자] 관리자 임명
    @PostMapping(value = "/admin/appointAdmin", consumes = "application/json")
    @ResponseBody
    public boolean appointAdmin(@RequestBody List<Map<String, String>> selectedMembers) {
        return selectedMembers.stream().allMatch(member -> adminService.appointAdmin(member.get("member_id")));
    }

    // 🔹 [관리자] 관리자 해임
    @PostMapping(value = "/admin/dismissAdmin", consumes = "application/json")
    @ResponseBody
    public boolean dismissAdmin(@RequestBody List<Map<String, String>> selectedMembers) {
        return selectedMembers.stream().allMatch(member -> adminService.dismissAdmin(member.get("member_id")));
    }

    // 🔹 [관리자] 회원 정보 수정
    @PostMapping(value = "/admin/modifyMember", consumes = "application/json")
    @ResponseBody
    public boolean modifyMember(@RequestBody List<Map<String, String>> selectedMembers) {
        return selectedMembers.stream().allMatch(member -> 
            adminService.modifyMember(
                member.get("member_id"), 
                member.get("member_level"), 
                member.get("member_exp")
            )
        );
    }

    // 🔹 [관리자] 회원 추방 & 블랙리스트 등록
    @PostMapping(value = "/admin/exileMember", consumes = "application/json")
    @ResponseBody
    public boolean exileMember(@RequestBody List<Map<String, String>> selectedMembers) {
        return selectedMembers.stream().allMatch(member -> adminService.exileMember(member.get("member_id")));
    }

    // 🔹 [관리자] 블랙리스트 해제
    @PostMapping(value = "/admin/exileMemberCancel", consumes = "application/json")
    @ResponseBody
    public boolean exileMemberCancel(@RequestBody List<Map<String, String>> selectedMembers) {
        return selectedMembers.stream().allMatch(member -> adminService.exileMemberCancel(member.get("member_id")));
    }
}
