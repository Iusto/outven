package com.example.outven.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.outven.entity.Champion_comment;

import jakarta.transaction.Transactional;

public interface ChampionCommentRepository extends JpaRepository<Champion_comment, Integer> {

    // ✅ 특정 챔피언의 댓글을 페이징 처리하여 조회
	Page<Champion_comment> findByChampCode(int champCode, Pageable pageable);

    // ✅ 특정 챔피언의 댓글 개수 조회
    long countByChampCode(int champCode);

    // ✅ com_re_ref가 일치하는 경우 com_re_seq를 업데이트
    @Transactional
    @Modifying
    @Query(value = "UPDATE champion_comment " +
                   "SET com_re_seq = com_re_seq + 1 " +
                   "WHERE com_re_ref = :comReRef " +
                   "AND com_re_seq > :comReSeq", nativeQuery = true)
    int updateComReSeq(@Param("comReRef") int comReRef, 
                       @Param("comReSeq") int comReSeq);
}
