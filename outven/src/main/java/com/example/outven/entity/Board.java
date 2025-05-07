package com.example.outven.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "BOARD_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "BOARD_SEQUENCE_GENERATOR", initialValue = 1, allocationSize = 1)
    @Column(name = "BOARDNUM")
    private int boardNum;

    @Column(name = "BOARDCATEGORY")
    private String boardCategory;

    @Column(name = "DETAILCATEGORY")
    private String detailCategory;

    @Column(name = "MEMBERID")
    private String memberId;

    @Column(name = "NICKNAME")
    private String nickName;

    @Column(name = "BOARDTITLE")
    private String boardTitle;

    @Column(name = "BOARDCONTENT")
    private String boardContent;

    @Column(name = "BOARDIMG")
    private String boardImg;

    @Column(name = "BOARDHIT")
    private int boardHit;

    @Column(name = "BOARDRECOMMEND")
    private int boardRecommend;

    @Column(name = "BOARDREPORTCOUNT")
    private int boardReportCount;

    @CreationTimestamp
    @Column(name = "CREATEDAT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATEDAT")
    private LocalDateTime updatedAt;
}
