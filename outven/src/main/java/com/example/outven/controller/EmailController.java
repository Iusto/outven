package com.example.outven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.outven.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private MemberService memberService;

    // ✅ [회원가입] 코드 이메일 전송
    @PostMapping("/mailCode")
    @ResponseBody
    public String mailCode(@RequestParam("email") String email) {
        logger.info("메일 인증 요청: {}", email);
        String code = memberService.mailCode(email);
        logger.info("메일 전송 완료, 인증 코드: {}", code);
        return code;
    }

    // ✅ [아이디 찾기] 코드 이메일 전송
    @PostMapping("/IDsearchMail")
    @ResponseBody
    public String IDsearchMail(@RequestParam("name") String name, @RequestParam("email") String email) {
        logger.info("아이디 찾기 메일 요청: 이름={}, 이메일={}", name, email);
        String code = memberService.idsearchMail(name, email);
        logger.info("아이디 찾기 인증 코드 전송 완료");
        return code;
    }

    // ✅ [비밀번호 찾기] 코드 이메일 전송
    @PostMapping("/PWsearchMail")
    @ResponseBody
    public String PWsearchMail(@RequestParam("id") String id, @RequestParam("email") String email) {
        logger.info("비밀번호 찾기 메일 요청: ID={}, 이메일={}", id, email);
        String code = memberService.pwsearchMail(id, email);
        logger.info("비밀번호 찾기 인증 코드 전송 완료");
        return code;
    }

    // ✅ [회원정보수정] 이메일 변경 시 코드 전송
    @PostMapping("/ModifyMail")
    @ResponseBody
    public String ModifyMail(@RequestParam("id") String id, @RequestParam("email") String email) {
        logger.info("이메일 변경 요청: ID={}, 새로운 이메일={}", id, email);
        String code = memberService.ModifyMail(id, email);
        logger.info("이메일 변경 인증 코드 전송 완료");
        return code;
    }

    // ✅ 이메일 중복 판별
    @PostMapping("/checkEmail")
    @ResponseBody
    public boolean checkEmail(@RequestParam("email") String email) {
        try {
            logger.info("이메일 중복 확인 요청: {}", email);
            boolean result = memberService.checkEmail(email);
            logger.info("이메일 중복 결과: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("이메일 중복 확인 오류 발생: {}", e.getMessage());
            return false;
        }
    }

    // ✅ 중복된 아이디 판별
    @PostMapping("/checkId")
    @ResponseBody
    public boolean checkId(@RequestParam("id") String id) {
        try {
            logger.info("아이디 중복 확인 요청: {}", id);
            boolean checkId = memberService.checkId(id);
            boolean blacklist = memberService.blacklistSearch(id);
            boolean result = blacklist || checkId;
            logger.info("아이디 중복 여부: {}, 블랙리스트 여부: {}, 최종 결과: {}", checkId, blacklist, result);
            return result;
        } catch (Exception e) {
            logger.error("아이디 중복 확인 오류 발생: {}", e.getMessage());
            return true; // 예외 발생 시 기본적으로 중복된 것으로 처리
        }
    }

    // ✅ 중복된 닉네임 판별
    @PostMapping("/checkNick")
    @ResponseBody
    public boolean checkNick(@RequestParam("nick") String nick) {
        try {
            logger.info("닉네임 중복 확인 요청: {}", nick);
            boolean result = memberService.checkNick(nick);
            logger.info("닉네임 중복 결과: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("닉네임 중복 확인 오류 발생: {}", e.getMessage());
            return false;
        }
    }
}
