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
    private int champCode;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    // 생성자
    public Championrate(int champCode, int rate, String memberId) {
        this.champCode = champCode;
        this.rate = rate;
        this.memberId = memberId;
    }
}