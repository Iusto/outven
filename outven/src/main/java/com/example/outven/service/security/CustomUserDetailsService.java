package com.example.outven.service.security;

import com.example.outven.entity.Member;
import com.example.outven.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;

	/** 
	 로그인 시 DB에서 사용자 정보를 로딩하는 클래스
	 UserDetails (Spring Security가 내부에서 사용)
	 Spring Security는 유저 인증을 이 클래스에 위임함
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Member member = memberRepository.findByMemberId(username)
	        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

	    // 🔥 블랙리스트 계정은 로그인 차단
//	    if (member.isBlacklist()) {
//	        throw new RuntimeException("정지된 계정입니다. 관리자에게 문의하세요.");
//	    }

	    return new User(
	        member.getMemberId(),
	        member.getPassword(),
	        Collections.singletonList(
	            new SimpleGrantedAuthority("ROLE_" + member.getUserRole().toUpperCase())
	        )
	    );
	}

}
