package com.example.outven.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.outven.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

    // 🔹 특정 역할(role)에 해당하는 회원을 조회 (페이징 적용)
    Page<Member> findByRole(String role, Pageable pageable);

    // 🔹 ID와 비밀번호로 회원 조회 (로그인)
    Member findByMemberIdAndPassword(String memberId, String password);

    // 🔹 회원의 역할(role) 변경 (관리자 임명 / 해임 / 블랙리스트 등록)
    @Transactional
    @Modifying
    @Query("UPDATE Member m SET m.role = :role WHERE m.member_id = :memberId")
    int updateMemberRole(@Param("memberId") String memberId, @Param("role") String role);

    // ✅ 회원 정보 업데이트 (레벨 & 경험치 수정)
    @Modifying
    @Transactional
    @Query(value = "UPDATE member SET member_level = :level, member_exp = CAST(:exp AS CHAR) WHERE member_id = :memberId", nativeQuery = true)
    int updateMemberInfo(@Param("memberId") String memberId, @Param("level") int level, @Param("exp") String exp);
    
    Optional<Member> findByMembernameAndEmail(String membername, String email); // 아이디 찾기
    Optional<Member> findByMemberIdAndEmail(String memberId, String email); // 비밀번호 찾기
    Optional<Member> findByMemberIdAndUser_password(String id, String password); // 로그인
    boolean existsByEmail(String email); // 이메일 중복
    boolean existsByMemberId(String id); // 아이디 중복
    boolean existsByNickName(String nick); // 닉네임 중복
}
