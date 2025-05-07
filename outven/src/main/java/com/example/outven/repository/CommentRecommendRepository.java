package com.example.outven.repository;

import com.example.outven.entity.CommentRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRecommendRepository extends JpaRepository<CommentRecommend, Long> {
    boolean existsByCommentNumAndMemberId(int commentNum, String memberId);
}
