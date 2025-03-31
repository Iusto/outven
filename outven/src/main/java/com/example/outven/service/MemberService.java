package com.example.outven.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.outven.dao.EmailDAO;
import com.example.outven.dto.memberDTO;
import com.example.outven.entity.Member;
import com.example.outven.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private EmailDAO edao;

    @Autowired
    private MemberRepository memberRepository;

    // 이메일 전송 관련 (DAO 유지)
    public String mailCode(String email) {
        return edao.mailCode(email);
    }

    public String idsearchMail(String name, String email) {
        return edao.idsearchMail(name, email);
    }

    public String pwsearchMail(String id, String email) {
        return edao.pwsearchMail(id, email);
    }

    public String ModifyMail(String id, String email) {
        return edao.ModifyMail(id, email);
    }

    // 🔍 아이디 찾기
    public String idsearch(String name, String email) {
        return memberRepository.findByMembernameAndEmail(name, email)
                .map(Member::getMember_id)
                .orElse(null);
    }

    // 🔒 비밀번호 찾기 인증
    public boolean pwsearch(String id, String email) {
        return memberRepository.findByMemberIdAndEmail(id, email).isPresent();
    }

    // 🔑 비밀번호 재설정
    public boolean pwReset(String id, String password) {
        return memberRepository.findById(id).map(member -> {
            member.setUser_password(password);
            memberRepository.save(member);
            return true;
        }).orElse(false);
    }

    // ✏️ 회원정보 수정
    public boolean memberModify(String id, Member updated) {
        return memberRepository.findById(id).map(member -> {
            member.setUser_password(updated.getUser_password());
            member.setEmail(updated.getEmail());
            member.setPhone(updated.getPhone());
            member.setAddress(updated.getAddress());
            member.setMembername(updated.getMembername());
            memberRepository.save(member);
            return true;
        }).orElse(false);
    }

    // 🧾 회원가입
    public boolean joinMember(memberDTO dto) {
        if (memberRepository.existsByMemberId(dto.getMember_id())) return false;
        Member member = new Member();
        BeanUtils.copyProperties(dto, member);
        memberRepository.save(member);
        return true;
    }

    // 🔐 로그인
    public Object[] login(String id, String pw) {
        return memberRepository.findByMemberIdAndUser_password(id, pw)
                .map(member -> new Object[]{member, false})
                .orElse(null);
    }

    // ✅ 중복 체크
    public boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean checkId(String id) {
        return memberRepository.existsByMemberId(id);
    }

    public boolean checkNick(String nick) {
        return memberRepository.existsByNickName(nick);
    }

    // ❌ 회원 탈퇴
    public boolean memberLeave(String id, String password) {
        return memberRepository.findByMemberIdAndUser_password(id, password).map(member -> {
            memberRepository.delete(member);
            return true;
        }).orElse(false);
    }

    // 🔎 블랙리스트 여부 (이건 DB에 별도 칼럼/테이블이 필요할 수 있음)
    public boolean blacklistSearch(String id) {
        return memberRepository.findById(id)
                .map(Member::isBlacklist)
                .orElse(false);
    }
}
