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

    @Column(name = "champ_code", nullable = false)
    private int champ_code;

    @Column(name = "skin_code", nullable = false)
    private int skin_code;

    @Column(name = "member_id", nullable = false)
    private String member_id;

    @Column(name = "rate", nullable = false)
    private int rate;

    // 명확한 생성자 추가
    public Champskinrate(int champ_code, int skin_code, String member_id, int rate) {
        this.champ_code = champ_code;
        this.skin_code = skin_code;
        this.member_id = member_id;
        this.rate = rate;
    }
}
