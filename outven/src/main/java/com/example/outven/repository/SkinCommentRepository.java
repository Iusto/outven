package com.example.outven.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.outven.entity.SkinComment;
import jakarta.transaction.Transactional;

public interface SkinCommentRepository extends JpaRepository<SkinComment, Integer> {

    // ✅ 스킨 코드가 일치한 댓글을 **페이징 처리**하여 조회 (JPA 방식)
    Page<SkinComment> findBySkinCode(int skinCode, Pageable pageable);

    // ✅ 챔피언 코드별 댓글 개수 조회
    long countByChampCode(int champCode);

    // ✅ 스킨 코드별 댓글 개수 조회
    long countBySkinCode(int skinCode);

    // ✅ 답글 작성 시 순서 업데이트
    @Transactional
    @Modifying
    @Query("UPDATE SkinComment s SET s.skin_re_seq = s.skin_re_seq + 1 WHERE s.skin_re_ref = :ref AND s.skin_re_seq > :seq")
    int updateSkinSeq(@Param("ref") int ref, @Param("seq") int seq);
}
