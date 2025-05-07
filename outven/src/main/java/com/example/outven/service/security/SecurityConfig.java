package com.example.outven.service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// 비밀번호 암호화 Bean 등록
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 사용자 인증 서비스 설정 (UserDetailsService 구현체 주입 예정)
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService(); // 직접 구현한 클래스 필요
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/index", "/joinForm", "/join", "/loginForm", "/login", "/logout",
						"/member/joinForm", "/member/loginForm", "/member/membersearch", "/member/idsearchForm",
						"/member/pwsearchForm", "/member/checkId", "/member/checkEmail", "/member/checkNickName",
						"/email/**", "/css/**", "/js/**", "/images/**", "/board/**", "/champ_board/**",
						"/champ_skin/**")
				.permitAll()
				.requestMatchers("/BoardWrite", "/writeForm", "/comment/**", "/checkCommentRecommend", "/member/**",
						"/board/modifyForm", "/board/modify", "/board/delete")
				.authenticated().anyRequest().permitAll())
				.formLogin(login -> login.loginPage("/loginForm").loginProcessingUrl("/login")
						.defaultSuccessUrl("/", true).permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
						.deleteCookies("JSESSIONID", "remember-me"))
				.rememberMe(rememberMe -> rememberMe.key("outven-remember-me-key").rememberMeParameter("remember-me")
						.tokenValiditySeconds(60 * 60 * 24 * 7).userDetailsService(userDetailsService()));

		return http.build();
	}

}
