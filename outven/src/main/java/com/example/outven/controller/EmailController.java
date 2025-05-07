package com.example.outven.controller;

import com.example.outven.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // 🔹 인증 코드 발송 요청
    @GetMapping("/sendCode")
    public String sendCode(@RequestParam String email) {
        boolean sent = emailService.sendAuthCode(email);
        return sent ? "success" : "fail";
    }

    // 🔹 인증 코드 검증 요청
    @PostMapping("/verifyCode")
    public String verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean result = emailService.verifyAuthCode(email, code);
        return result ? "success" : "fail";
    }
}
