package com.example.outven.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.outven.dto.MemberDTO;
import com.example.outven.entity.Member;
import com.example.outven.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 🔹 로그인
    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String pw = request.getParameter("password");

        String status = memberService.loginStatus(id, pw);

        if (!status.equals("SUCCESS")) {
            model.addAttribute("loginError", status);
            return "member/loginForm"; // 실패 시 로그인 폼으로
        }

        Member member = memberService.findMemberOrThrow(id);
        request.getSession().setAttribute("member", member);
        return "redirect:/";
    }

    // 🔹 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "member/logout";
    }

    // 🔹 로그인 폼
    @GetMapping("/loginForm")
    public String loginForm() {
        return "member/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(HttpSession session, Model model, HttpServletResponse response) throws IOException {
        if (session.getAttribute("member") != null) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(
                "<script>alert('잘못된 접근입니다.'); location.href='/';</script>"
            );
            return null;
        }
        model.addAttribute("member", new MemberDTO());  // 🔥 이 줄 추가
        return "member/joinForm";
    }



    // 🔹 ID/PW 찾기 선택창
    @GetMapping("/membersearch")
    public String memberSearch() {
        return "member/memberSearch";
    }

    // 🔹 ID 찾기 폼
    @GetMapping("/idsearchForm")
    public String idsearchForm() {
        return "member/idsearchForm";
    }

    // 🔹 PW 찾기 폼
    @GetMapping("/pwsearchForm")
    public String pwsearchForm() {
        return "member/pwsearchForm";
    }

    // 🔹 회원정보 수정 폼
    @GetMapping("/modifyForm")
    public String modifyForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        model.addAttribute("member", member);
        return "member/modifyForm";
    }

    // 🔹 회원정보 수정
    @PostMapping("/modify")
    public String modify(HttpServletRequest request, Model model) {
        String id = request.getParameter("member_id");

        String pwd = request.getParameter("password1");
        String name = request.getParameter("membername");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        if (address == null) {
            address = request.getParameter("address1") + " " + request.getParameter("address2");
        }

        Member member = new Member();
        member.setPassword(pwd);
        member.setEmail(email);
        member.setPhone(phone);
        member.setAddress(address);
        member.setMembername(name);

        boolean result = memberService.memberModify(id, member);

        if (result) {
            HttpSession session = request.getSession();
            session.invalidate();
        }

        model.addAttribute("result", result);
        return "member/modify";
    }

    // 🔹 회원가입
    @PostMapping("/join")
    public String join(@ModelAttribute MemberDTO memberDto, RedirectAttributes redirectAttributes) {
        // address 합치기
        memberDto.setAddress(memberDto.getAddress1() + " " + memberDto.getAddress2());

        boolean result = memberService.joinMember(memberDto);

        if (result) {
            redirectAttributes.addFlashAttribute("joinSuccess", true);
            return "redirect:/member/loginForm";
        }
        return "member/joinFail";
    }

    // 🔹 아이디 찾기
    @PostMapping("/idsearch")
    public String idsearch(HttpServletRequest request, Model model) {
        String name = request.getParameter("membername");
        String email = request.getParameter("email");
        String id = memberService.idsearch(name, email);

        if (id != null) {
            model.addAttribute("id", id);
            return "member/idFind";
        }
        return "member/idNotFind";
    }

    // 🔹 비밀번호 찾기
    @PostMapping("/pwsearch")
    public String pwsearch(HttpServletRequest request, Model model) {
        String id = request.getParameter("member_id");
        String email = request.getParameter("email");

        boolean result = memberService.pwsearch(id, email);

        if (result) {
            model.addAttribute("id", id);
            return "member/pwResetForm";
        }
        return "member/pwResetFail";
    }

    // 🔹 비밀번호 재설정
    @PostMapping("/pwReset")
    public String pwreset(HttpServletRequest request, Model model) {
        String id = request.getParameter("member_id");
        String password = request.getParameter("password1");

        boolean result = memberService.pwReset(id, password);

        if (result) {
            return "member/pwResetOk";
        }
        return "member/pwResetNo";
    }

    // 🔹 회원 탈퇴 폼
    @GetMapping("/memberLeaveForm")
    public String memberLeaveForm(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        model.addAttribute("member", member);
        return "member/memberLeaveForm";
    }

    // 🔹 회원 탈퇴
    @PostMapping("/memberLeave")
    public String memberLeave(HttpServletRequest request, Model model) {
        String id = request.getParameter("member_id");
        String password = request.getParameter("password1");

        boolean result = memberService.memberLeave(id, password);

        if (result) {
            HttpSession session = request.getSession();
            session.invalidate();
        }

        model.addAttribute("result", result);
        return "member/memberLeave";
    }

    // 🔹 아이디 중복 검사
    @GetMapping("/member/checkId")
    @ResponseBody
    public boolean checkId(@RequestParam("id") String id) {
        return memberService.checkId(id);
    }


    // 🔹 이메일 중복 검사
    @GetMapping("/member/checkEmail")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        return memberService.checkEmail(email);
    }
    
    // 🔹 닉네임 중복 검사
    @GetMapping("/member/checkNickName")
    @ResponseBody
    public boolean checkNickName(@RequestParam("nickName") String nickName) {
        return memberService.checkNick(nickName); 
    }

}
