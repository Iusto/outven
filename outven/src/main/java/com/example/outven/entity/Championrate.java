package com.example.outven.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "championrate")
public class Championrate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "champ_code", nullable = false)
    private int champ_code;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "member_id", nullable = false)
    private String member_id;

    // 추가된 생성자
    public Championrate(int champCode, int rate, String memberId) {
        this.champ_code = champCode;
        this.rate = rate;
        this.member_id = memberId;
    }
}
