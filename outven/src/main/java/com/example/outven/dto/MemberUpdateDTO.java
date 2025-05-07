package com.example.outven.dto;

import lombok.Data;

@Data
public class MemberUpdateDTO {
    private String memberId;
    private int level;
    private int exp;
}
