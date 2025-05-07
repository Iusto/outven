package com.example.outven.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "champ_skin")
public class Champ_skin {

    // 챔피언 스킨 기본키 (자동 증가)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SKIN_SEQUENCE_GENERATOR")
    @SequenceGenerator(
        name = "SKIN_SEQUENCE_GENERATOR",
        sequenceName = "champ_sk_seq",
        initialValue = 1,
        allocationSize = 1
    )
    @Column(name = "skin_code")
    private int skinCode; // ← 필드명을 camelCase로 변경!

    @Column(name = "champ_code")
    private int champCode;

    @Column(nullable = false, length = 100)
    private String champ_skin_name; // 스킨 이름

    @Column(nullable = false)
    private double champ_skin_rate; // 챔프 스킨 평점 (✅ 기존 rate 필드)

    @Column(nullable = false, length = 255)
    private String champ_icon_img; // 챔프 아이콘 이미지 경로

    @Column(nullable = false, length = 255)
    private String champ_skin_img; // 챔피언 스킨 이미지 경로

    @Column(nullable = false)
    private int skin_rp; // 스킨 가격

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date skin_logtime; // 생성일

 // ✅ 챔피언 스킨 평점 수정 메서드 추가
    public void setChampSkinRate(double rate) {
        this.champ_skin_rate = rate;
    }

}
