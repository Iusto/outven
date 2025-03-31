package com.example.outven.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.outven.entity.Board;
import com.example.outven.entity.Member;
import com.example.outven.repository.BoardRepository;
import com.example.outven.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;

    // 🔹 특정 역할(role)에 해당하는 회원을 조회 (페이징 적용)
    public Page<Member> getMembersByRole(String role, Pageable pageable) {
        return memberRepository.findByRole(role, pageable);
    }

    // 🔹 관리자 로그인
    public Member Adminlogin(String id, String pw) {
        return memberRepository.findByMemberIdAndPassword(id, pw);
    }

    // 🔹 관리자 임명
    public boolean appointAdmin(String memberId) {
        int updatedRows = memberRepository.updateMemberRole(memberId, "ADMIN");
        return updatedRows > 0;
    }

    // 🔹 관리자 해임
    public boolean dismissAdmin(String memberId) {
        int updatedRows = memberRepository.updateMemberRole(memberId, "USER");
        return updatedRows > 0;
    }

    // 🔹 회원 정보 수정
    public boolean modifyMember(String memberId, String level, String exp) {
        try {
            int parsedLevel = Integer.parseInt(level);
            int updatedRows = memberRepository.updateMemberInfo(memberId, parsedLevel, exp);
            return updatedRows > 0;
        } catch (NumberFormatException e) {
            // level이 숫자가 아닐 경우 예외 처리
            return false;
        }
    }

    // 🔹 회원 블랙리스트 등록
    public boolean exileMember(String memberId) {
        int updatedRows = memberRepository.updateMemberRole(memberId, "BLACKLIST");
        return updatedRows > 0;
    }

    // 🔹 블랙리스트 해제
    public boolean exileMemberCancel(String memberId) {
        int updatedRows = memberRepository.updateMemberRole(memberId, "USER");
        return updatedRows > 0;
    }
    
    @Transactional
    public boolean adminBoardMove(int boardNum, String boardCategory, String detailCategory) {
        Optional<Board> boardOptional = boardRepository.findById(boardNum);
        if (boardOptional.isPresent()) {
            Board board = boardOptional.get();
            board.setBoard_category(boardCategory);
            board.setDetail_category(detailCategory);
            boardRepository.save(board);
            return true;
        }
        return false;
    }

}
