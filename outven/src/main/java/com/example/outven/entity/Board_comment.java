package com.example.outven.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Board_comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_num;
    private int boardNum;
    private String comment_content;
    private String member_id;
    private String nick_name;
    @Column(name = "comment_logtime")
    private LocalDateTime commentLogtime;
    private int recommend;

    // 🔹 대댓글 기능을 위한 부모 댓글 번호 (null이면 일반 댓글)
    private Integer parentCommentNum;
}
