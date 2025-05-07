package com.example.outven.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Champskinrate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @Column(name = "champ_code")
    private int champCode;

    @Column(name = "skin_code")
    private int skinCode;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "rate", nullable = false)
    private int rate;

    // 명확한 생성자 추가
    public Champskinrate(int champCode, int skinCode, String memberId, int rate) {
        this.champCode = champCode;
        this.skinCode = skinCode;
        this.memberId = memberId;
        this.rate = rate;
    }
}
