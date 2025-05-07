package com.example.outven.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CommentRecommend {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private int commentNum;
    private String memberId;
    
    public CommentRecommend() {}
    
    public CommentRecommend(int commentNum, String memberId) {
        this.commentNum = commentNum;
        this.memberId = memberId;
    }
}