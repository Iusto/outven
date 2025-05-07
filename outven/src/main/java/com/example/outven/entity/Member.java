package com.example.outven.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
public class Member {

	@Id
    @Column(name = "MEMBERID")
	private String memberId;

	private String membername;
	private String nickName;
	private String password;
	private String email;
	private String phone;
	private String address;
	private String gender;

	@Column(name = "user_role") // DB는 그대로 유지 가능
	private String userRole; // 권한 (user, admin)
	private Date jointime; // 가입일자 (java.sql.Date)
	private String birth; // 생년월일 (yyyy-MM-dd 문자열)

	private String member_level; // 레벨은 문자열 (DB 저장용)
	private String member_exp; // 경험치도 문자열
	@Column(nullable = false)
	private boolean blacklist = false;	// 블랙리스트 여부
	private String banReason;  // 밴 사유
	private LocalDateTime banUntil; // 남은 밴 기간
}
