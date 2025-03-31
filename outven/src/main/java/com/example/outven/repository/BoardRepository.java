package com.example.outven.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.outven.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 🔹 전체 게시글 페이징 조회
    Page<Board> findAll(Pageable pageable);

    // 🔹 특정 카테고리의 게시글 조회 (페이징 적용)
    Page<Board> findByBoardCategory(String boardCategory, Pageable pageable);

    // 🔹 특정 카테고리 & 상세 카테고리 게시글 조회 (페이징 적용)
    Page<Board> findByBoardCategoryAndDetailCategory(String boardCategory, String detailCategory, Pageable pageable);

    // 🔹 추천수가 일정 이상인 게시글 조회 (페이징 적용)
    Page<Board> findByRecommendGreaterThanEqual(int recommend, Pageable pageable);

    // 🔹 신고 횟수가 일정 이상인 게시글 조회 (페이징 적용)
    Page<Board> findByReportCountGreaterThanEqual(int reportCount, Pageable pageable);

    // 🔹 제목으로 검색 (페이징 적용)
    Page<Board> findByTitleContainingAndBoardCategory(String keyword, String boardCategory, Pageable pageable);

    // 🔹 내용으로 검색 (페이징 적용)
    Page<Board> findByContentContainingAndBoardCategory(String keyword, String boardCategory, Pageable pageable);

    // 🔹 닉네임으로 검색 (페이징 적용)
    Page<Board> findByNickNameContainingAndBoardCategory(String keyword, String boardCategory, Pageable pageable);

    // 🔹 작성자(회원 ID)로 검색 (페이징 적용)
    Page<Board> findByMemberIdContainingAndBoardCategory(String keyword, String boardCategory, Pageable pageable);

    // 🔹 제목 또는 내용으로 검색 (페이징 적용)
    Page<Board> findByTitleContainingOrContentContainingAndBoardCategory(
        String titleKeyword, String contentKeyword, String boardCategory, Pageable pageable
    );

    // 🔹 검색된 게시글 개수 조회
    long countByTitleContainingOrContentContaining(String keyword);

    // 🔹 특정 게시글 조회
    Optional<Board> findById(int boardNum);

    // 🔹 조회수 증가 (updateHit)
    @Transactional
    @Modifying
    @Query("UPDATE Board b SET b.board_hit = b.board_hit + 1 WHERE b.board_num = :boardNum")
    void updateHit(@Param("boardNum") int boardNum);

    // 🔹 특정 카테고리의 게시글 개수 조회
    long countByBoardCategory(String boardCategory);

    // 🔹 특정 상세 카테고리의 게시글 개수 조회
    long countByBoardCategoryAndDetailCategory(String boardCategory, String detailCategory);

	Page<Board> findByTitleContaining(String keyword, Pageable pageable);
}
