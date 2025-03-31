package com.example.outven.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.outven.entity.Skin_comment;
import jakarta.transaction.Transactional;

public interface SkinCommentRepository extends JpaRepository<Skin_comment, Integer> {

    // ✅ 스킨 코드가 일치한 댓글을 **페이징 처리**하여 조회 (JPA 방식)
    Page<Skin_comment> findBySkinCode(int skinCode, Pageable pageable);

    // ✅ 챔피언 코드별 댓글 개수 조회
    long countByChampCode(int champCode);

    // ✅ 스킨 코드별 댓글 개수 조회
    long countBySkinCode(int skinCode);

    // ✅ 답글 작성 시 순서 업데이트
    @Transactional
    @Modifying
    @Query("UPDATE Skin_comment c SET c.skin_re_seq = c.skin_re_seq + 1 " +
           "WHERE c.skin_re_ref = :skinReRef AND c.skin_re_seq > :skinReSeq")
    int updateSkinSeq(@Param("skinReRef") int skinReRef, @Param("skinReSeq") int skinReSeq);
}
