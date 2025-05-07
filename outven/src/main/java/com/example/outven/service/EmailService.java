package com.example.outven.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    private final JavaMailSender emailSender;
    private final Map<String, String> codeStore = new ConcurrentHashMap<>(); // 이메일-코드 매핑

    // 인증 코드 저장용 Map (→ 추후 Redis 대체 가능)
    private Map<String, String> authCodeStorage;

    @PostConstruct
    public void init() {
        authCodeStorage = new ConcurrentHashMap<>();
    }

    // 🔹 인증코드 생성 (6자리 숫자)
    private String createCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    // 🔹 인증 이메일 발송 및 코드 저장
    public boolean sendAuthCode(String email) {
        try {
            String code = createCode();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[Outven] 이메일 인증 코드 안내");
            message.setText("Outven 회원가입 인증 코드입니다.\n\n인증 코드: " + code + "\n\n인증 화면에 입력해주세요.");
            mailSender.send(message);

            authCodeStorage.put(email, code); // 임시 저장
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 인증 코드 확인
    public boolean verifyAuthCode(String email, String inputCode) {
        String storedCode = authCodeStorage.get(email);
        return storedCode != null && storedCode.equals(inputCode);
    }

    // 🔹 인증 코드 삭제 (인증 완료 이후 권장)
    public void removeAuthCode(String email) {
        authCodeStorage.remove(email);
    }

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    // 인증코드 발송
    public String sendCode(String email) {
        String code = generateCode();
        codeStore.put(email, code); // 인증코드 저장
        sendSimpleMessage(email, "회원가입 인증코드", "인증번호는: " + code);
        return code;
    }

    // 인증코드 검증
    public boolean verifyCode(String email, String inputCode) {
        String savedCode = codeStore.get(email);
        return savedCode != null && savedCode.equals(inputCode);
    }

    private String generateCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000); // 6자리 숫자
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
