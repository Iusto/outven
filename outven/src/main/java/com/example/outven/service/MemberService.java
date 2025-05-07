package com.example.outven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.outven.dto.MemberDTO;
import com.example.outven.dto.MemberUpdateDTO;
import com.example.outven.entity.Member;
import com.example.outven.repository.MemberRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder; // 🔐 추가

	// 🔍 아이디 찾기
	public String idsearch(String name, String email) {
		return memberRepository.findByMembernameAndEmail(name, email).map(Member::getMemberId).orElse(null);
	}

	// 🔒 비밀번호 찾기 인증
	public boolean pwsearch(String id, String email) {
		return memberRepository.findByMemberIdAndEmail(id, email).isPresent();
	}

	// 🔑 비밀번호 재설정
	public boolean pwReset(String id, String password) {
		return memberRepository.findById(id).map(member -> {
			member.setPassword(passwordEncoder.encode(password)); // 🔐 암호화 적용
			memberRepository.save(member);
			return true;
		}).orElse(false);
	}

	// ✏️ 회원정보 수정
	public boolean memberModify(String id, Member updated) {
		return memberRepository.findById(id).map(member -> {
			member.setPassword(passwordEncoder.encode(updated.getPassword())); // 🔐 암호화 적용
			member.setEmail(updated.getEmail());
			member.setPhone(updated.getPhone());
			member.setAddress(updated.getAddress());
			member.setMembername(updated.getMembername());
			memberRepository.save(member);
			return true;
		}).orElse(false);
	}

	// 🧾 회원가입
	public boolean joinMember(MemberDTO dto) {
		try {
			Member member = new Member();

			member.setMemberId(dto.getMemberId());
			member.setPassword(passwordEncoder.encode(dto.getPassword())); // 🔐 암호화 적용
			member.setMembername(dto.getMemberName());
			member.setNickName(dto.getNickName());
			member.setEmail(dto.getEmail());
			member.setPhone(dto.getPhone());
			member.setAddress(dto.getAddress());
			member.setGender(dto.getGender());

			member.setUserRole(dto.getUserRole());
			member.setJointime(Date.valueOf(dto.getJoinDate() != null ? dto.getJoinDate() : LocalDate.now()));
			member.setBirth(dto.getBirthDate().toString());
			member.setMember_level(String.valueOf(dto.getLevel()));
			member.setMember_exp(String.valueOf(dto.getExp()));

			memberRepository.save(member);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 로그인
	public Object[] login(String id, String pw) {
	    return memberRepository.findByMemberId(id)
	        .filter(member -> !member.isBlacklist()) // 🔒 블랙리스트 여부 확인
	        .filter(member -> passwordEncoder.matches(pw, member.getPassword()))
	        .map(member -> new Object[] { member, false })
	        .orElse(null);
	}

	// ✅ 중복 체크
	public boolean checkEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	public boolean checkId(String id) {
		return memberRepository.existsByMemberId(id);
	}

	public boolean checkNick(String nick) {
		return memberRepository.existsByNickName(nick);
	}

	// ❌ 회원 탈퇴
	public boolean memberLeave(String id, String password) {
		return memberRepository.findByMemberId(id).map(member -> {
			if (passwordEncoder.matches(password, member.getPassword())) {
				memberRepository.delete(member);
				return true;
			}
			return false;
		}).orElse(false);
	}

	// 🔎 블랙리스트 여부
	public boolean blacklistSearch(String id) {
		return memberRepository.findById(id).map(Member::isBlacklist).orElse(false);
	}

	public void bulkUpdateMembers(List<MemberUpdateDTO> updateList) {
		for (MemberUpdateDTO dto : updateList) {
			memberRepository.updateMemberInfo(dto.getMemberId(), dto.getLevel(), String.valueOf(dto.getExp()));
		}
	}
	
	public String loginStatus(String id, String pw) {
	    Optional<Member> optional = memberRepository.findByMemberId(id);
	    if (optional.isEmpty()) return "ID_NOT_FOUND";

	    Member member = optional.get();
	    if (member.isBlacklist()) return "BLACKLIST";

	    if (!passwordEncoder.matches(pw, member.getPassword())) return "WRONG_PASSWORD";

	    return "SUCCESS";
	}
	
	public Member findMemberOrThrow(String id) {
	    return memberRepository.findByMemberId(id)
	            .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다: " + id));
	}

}
