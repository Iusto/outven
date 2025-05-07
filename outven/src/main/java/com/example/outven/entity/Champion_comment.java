package com.example.outven.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Champion_comment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHAMPION_COMMENT_SEQ_GEN")
	@SequenceGenerator(
	        name = "CHAMPION_COMMENT_SEQ_GEN",
	        sequenceName = "champ_comment_seq",
	        initialValue = 1,
	        allocationSize = 1
	    )
	private int champ_com_num;  // 챔피언 댓글 번호

	@Column(name = "champ_code")
	private int champCode; // 필드명은 camelCase, 컬럼명은 그대로 유지
	private String member_id;   // ID
	private String nick_name;   // 회원 별명
	private String champ_comment;  // 회원 댓글

	@Temporal(TemporalType.DATE)
	private Date comment_logtime;  // 게시일

    private int com_re_ref;  // 회원 그룹 번호(답글용)
    private int com_re_lev;  // 회원 단계
    private int com_re_seq;  // 회원 글순서

    @Transient
    private boolean com_judge;
}
