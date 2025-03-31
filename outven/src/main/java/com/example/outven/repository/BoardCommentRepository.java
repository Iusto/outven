package com.example.outven.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.outven.entity.Board_comment;

import jakarta.transaction.Transactional;

public interface BoardCommentRepository extends JpaRepository<Board_comment, Integer> {

    // 특정 게시글의 댓글 목록 조회 (페이징 적용)
    Page<Board_comment> findByBoardNum(int boardNum, Pageable pageable);

    // 게시글 번호와 일치하는 댓글 수 조회
    long countByBoardNum(int boardNum);

    // 대댓글 정렬을 위한 `com_re_seq` 업데이트
    @Transactional
    @Modifying
    @Query(value = "UPDATE board_comment SET com_re_seq = com_re_seq + 1 "
                 + "WHERE com_re_ref = :com_re_ref AND com_re_seq > :com_re_seq",
           nativeQuery = true)
    int updateReseq(@Param("com_re_ref") int com_re_ref, @Param("com_re_seq") int com_re_seq);
}
