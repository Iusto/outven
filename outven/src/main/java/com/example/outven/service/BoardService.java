package com.example.outven.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.outven.dto.BoardDTO;
import com.example.outven.entity.Board;
import com.example.outven.entity.Board_comment;
import com.example.outven.repository.BoardRepository;
import com.example.outven.repository.BoardCommentRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCommentRepository boardCommentRepository;

    // ✅ BoardDTO → Board 변환 메서드
    private Board convertToEntity(BoardDTO dto) {
        Board board = new Board();
        BeanUtils.copyProperties(dto, board); // DTO 필드 → Entity 필드 매핑
        return board;
    }

    // ✅ 게시글 작성 (BoardDTO → Entity 변환 후 저장)
    public boolean createBoard(BoardDTO dto) {
        try {
            Board board = convertToEntity(dto);
            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ 게시글 수정 (기존 게시글 정보 덮어쓰기)
    public boolean updateBoard(BoardDTO dto) {
        try {
            Board board = convertToEntity(dto);
            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ 카테고리별 게시글 목록 조회 (페이징 적용)
    public Page<Board> getBoardListByCategory(String boardCategory, Pageable pageable) {
        return boardRepository.findByBoardCategory(boardCategory, pageable);
    }

    // ✅ 특정 게시글 상세 조회 (board_num 기준)
    public Board boardView(int boardNum) {
        return boardRepository.findById(boardNum)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다: " + boardNum));
    }

    // ✅ 댓글 목록 조회 (게시글 번호 기준 페이징 적용)
    public Page<Board_comment> getCommentList(int boardNum, Pageable pageable) {
        return boardCommentRepository.findByBoardNum(boardNum, pageable);
    }

    // ✅ 게시글 엔티티 직접 저장 (수정 포함)
    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    // ✅ 특정 카테고리의 전체 게시글 수 반환
    public long getBoardCount(String boardCategory) {
        return boardRepository.countByBoardCategory(boardCategory);
    }

    // ✅ 카테고리 + 상세카테고리 기준 게시글 수 반환
    public long getDetailCount(String boardCategory, String detailCategory) {
        return boardRepository.countByBoardCategoryAndDetailCategory(boardCategory, detailCategory);
    }

    // ✅ 카테고리 + 상세카테고리 기준 게시글 목록 조회
    public Page<Board> getBoardsByCategoryAndDetail(String boardCategory, String detailCategory, Pageable pageable) {
        return boardRepository.findByBoardCategoryAndDetailCategory(boardCategory, detailCategory, pageable);
    }

    // ✅ 게시글 삭제 (board_num 기준)
    public boolean deleteBoard(int boardNum) {
        try {
            boardRepository.deleteById(boardNum);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ 조회수 증가 처리
    public void updateHit(int boardNum) {
        boardRepository.updateHit(boardNum);
    }

    // ✅ 추천수 기준으로 게시글 조회 (추천수 ≥ threshold)
    public Page<Board> getBoardsByRecommendGreaterThanEqual(int threshold, Pageable pageable) {
        return boardRepository.findByRecommendGreaterThanEqual(threshold, pageable);
    }

    // ✅ 메인화면용 추천 게시글 조회 (alias)
    public Page<Board> getRecommendedBoards(int recommendThreshold, Pageable pageable) {
        return getBoardsByRecommendGreaterThanEqual(recommendThreshold, pageable);
    }

    // 🔍 제목 검색 (카테고리 조건 포함)
    public Page<Board> searchByTitleAndCategory(String keyword, String category, Pageable pageable) {
        return boardRepository.findByTitleContainingAndBoardCategory(keyword, category, pageable);
    }

    // 🔍 내용 검색 (카테고리 조건 포함)
    public Page<Board> searchByContentAndCategory(String keyword, String category, Pageable pageable) {
        return boardRepository.findByContentContainingAndBoardCategory(keyword, category, pageable);
    }

    // 🔍 닉네임 검색 (카테고리 조건 포함)
    public Page<Board> searchByNickNameAndCategory(String keyword, String category, Pageable pageable) {
        return boardRepository.findByNickNameContainingAndBoardCategory(keyword, category, pageable);
    }

    // 🔍 작성자 ID 검색 (카테고리 조건 포함)
    public Page<Board> searchByMemberIdAndCategory(String keyword, String category, Pageable pageable) {
        return boardRepository.findByMemberIdContainingAndBoardCategory(keyword, category, pageable);
    }

    // 🔍 제목 또는 내용 검색 (카테고리 조건 포함)
    public Page<Board> searchByTitleOrContentAndCategory(String title, String content, String category, Pageable pageable) {
        return boardRepository.findByTitleContainingOrContentContainingAndBoardCategory(title, content, category, pageable);
    }
    
    // ✅ 제목 기준 통합 검색 (전체 게시판 대상)
    public Page<Board> searchByTitle(String keyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(keyword, pageable);
    }
}
