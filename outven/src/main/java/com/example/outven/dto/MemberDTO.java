package com.example.outven.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberDTO {

	private String memberId; // 사용자 ID
	private String password; // 비밀번호
	private String memberName; // 이름
	private String nickName; // 닉네임
	private String email; // 이메일
	private String phone; // 전화번호
	private String gender; // 성별
	private String address1; // 주소1
	private String address2; // 주소2
	private String address; // 기본 주소 + 상세 주소

	private int year; // 생년
	private int month; // 생월
	private int day; // 생일
	private LocalDate birthDate; // 생년월일 LocalDate 버전

	private String userRole; // 역할 (USER, ADMIN 등)
	private LocalDate joinDate; // 가입일

	private int level; // 회원 등급
	private int exp; // 경험치

	private boolean blacklist; // 블랙리스트 여부
	private String banReason; // 정지 사유
	private LocalDate banUntil; // 정지 만료일
}
