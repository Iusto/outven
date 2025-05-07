package com.example.outven.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.outven.entity.Board_comment;

import jakarta.transaction.Transactional;

public interface BoardCommentRepository extends JpaRepository<Board_comment, Integer> {

	// 특정 게시글의 댓글 목록 조회 (대댓글 포함)
	Page<Board_comment> findByBoardNum(int boardNum, Pageable pageable);

	// 부모 댓글 기준으로 대댓글 가져오기
	List<Board_comment> findByParentCommentNum(int parentCommentNum);

	@Modifying
	@Transactional
	@Query("UPDATE Board_comment c SET c.recommend = c.recommend + 1 WHERE c.comment_num = :commentNum")
	void incrementRecommend(@Param("commentNum") int commentNum);
	}