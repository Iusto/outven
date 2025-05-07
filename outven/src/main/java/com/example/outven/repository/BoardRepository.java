package com.example.outven.repository;

import com.example.outven.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // ✅ 전체 게시글 목록 (페이징)
    Page<Board> findAll(Pageable pageable);

    // ✅ 카테고리별 게시글 목록 조회
    Page<Board> findByBoardCategory(String boardCategory, Pageable pageable);

    // ✅ 카테고리 + 상세 카테고리별 게시글 목록 조회
    Page<Board> findByBoardCategoryAndDetailCategory(String boardCategory, String detailCategory, Pageable pageable);

    // ✅ 추천수가 일정 수 이상인 게시글 목록 조회
    Page<Board> findByBoardRecommendGreaterThanEqual(int recommend, Pageable pageable);

    // ✅ 제목으로 검색 + 카테고리 포함
    Page<Board> findByBoardTitleContainingAndBoardCategory(String keyword, String boardCategory, Pageable pageable);

    // ✅ 내용으로 검색 + 카테고리 포함
    Page<Board> findByBoardContentContainingAndBoardCategory(String keyword, String boardCategory, Pageable pageable);

    // ✅ 작성자 닉네임으로 검색 + 카테고리 포함
    Page<Board> findByNickNameContainingAndBoardCategory(String keyword, String boardCategory, Pageable pageable);

    // ✅ 작성자 아이디로 검색 + 카테고리 포함
    Page<Board> findByMemberIdContainingAndBoardCategory(String keyword, String boardCategory, Pageable pageable);

    // ✅ 제목 또는 내용으로 검색 + 카테고리 포함
    Page<Board> findByBoardTitleContainingOrBoardContentContainingAndBoardCategory(
        String titleKeyword, String contentKeyword, String boardCategory, Pageable pageable
    );

    // ✅ 제목 또는 내용으로 검색된 게시글 수 조회
    long countByBoardTitleContainingOrBoardContentContaining(String keyword1, String keyword2);

    // ✅ 게시글 번호로 단일 게시글 조회
    Optional<Board> findById(int boardNum);

    // ✅ 게시글 조회수 증가 쿼리 (직접 update)
    @Transactional
    @Modifying
    @Query("UPDATE Board b SET b.boardHit = b.boardHit + 1 WHERE b.boardNum = :boardNum")
    void updateHit(@Param("boardNum") int boardNum);

    // ✅ 카테고리별 게시글 개수 조회
    long countByBoardCategory(String boardCategory);

    // ✅ 카테고리 + 상세 카테고리별 게시글 개수 조회
    long countByBoardCategoryAndDetailCategory(String boardCategory, String detailCategory);

    // ✅ 제목 기반 통합 검색 (카테고리 무관)
    Page<Board> findByBoardTitleContaining(String keyword, Pageable pageable);
}
