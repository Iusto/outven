package com.example.outven.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "member")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
	@Column(name = "member_id")
    private String member_id;    	// 아이디(고유값)
    private String membername;    	// 이름
    private String nick_name; 		// 별명
    private String user_password; 	// 패스워드
    private String email; 			// 이메일
    private String phone; 			// 전화번호
    private String birth;			// 생년월일
    private String address; 		// 주소
    private String gender; 			// 성별(남자면 m 여자면 f)
    private String user_role;  		// 관리자 유무
    @Temporal(TemporalType.DATE)
    private Date logtime;  			// 마지막 로그인 날짜
    @Temporal(TemporalType.DATE)
    private Date jointime;  		// 회원가입날짜
    @Column(name = "member_level")
    private int member_level;       // 레벨
    @Column(name = "member_exp")
    private String memberExp;  		// 경험치
    @Column(name = "role")
    private String role;			// 역할
    
    private boolean blacklist; // ✅ 블랙리스트 여부

    public boolean isBlacklist() {
        return blacklist;
    }

    public void setBlacklist(boolean blacklist) {
        this.blacklist = blacklist;
    }
}
