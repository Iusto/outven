package com.example.outven.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.outven.dto.MemberUpdateDTO;
import com.example.outven.entity.Member;
import com.example.outven.repository.MemberRepository;
import com.example.outven.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberService memberService;

	// 관리자 페이지 접근
	@GetMapping("/manage")
	public String managePage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");

		if (member == null || !"admin".equals(member.getUserRole())) {
			return "redirect:/loginForm";
		}

		List<Member> members = memberRepository.findAll();
		model.addAttribute("members", members);
		return "admin/manage";
	}

	// ✅ 레벨/경험치 업데이트
	@PostMapping("/update-level-exp")
	public String updateLevelExp(@RequestParam(required = false) List<String> checkedIds,
			@RequestParam Map<String, String> levelMap, @RequestParam Map<String, String> expMap) {

		if (checkedIds != null) {
			for (String memberId : checkedIds) {
				try {
					int level = Integer.parseInt(levelMap.get("[" + memberId + "]"));
					String exp = expMap.get("[" + memberId + "]");

					memberRepository.updateMemberInfo(memberId, level, exp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/admin/manage";
	}

	// ✅ 관리자 권한 변경
	@PostMapping("/update-role")
	public String updateRole(@RequestParam Map<String, String> roleMap) {
		for (Map.Entry<String, String> entry : roleMap.entrySet()) {
			String memberId = entry.getKey().replaceAll("[\\[\\]]", "");
			String role = entry.getValue();

			memberRepository.updateMemberRole(memberId, role);
		}
		return "redirect:/admin/manage";
	}

	// ✅ 블랙리스트 설정
	@PostMapping("/update-blacklist")
	public String updateBlacklist(@RequestParam Map<String, String> blacklistMap) {
		for (Map.Entry<String, String> entry : blacklistMap.entrySet()) {
			String memberId = entry.getKey().replaceAll("[\\[\\]]", "");
			boolean isBlacklisted = Boolean.parseBoolean(entry.getValue());

			memberRepository.findById(memberId).ifPresent(member -> {
				member.setBlacklist(isBlacklisted);
				memberRepository.save(member);
			});
		}
		return "redirect:/admin/manage";
	}

	// 🔐 관리자 전용 - 멤버 정보 일괄 업데이트 (레벨 & 경험치)
	@PostMapping("/updateMembers")
	public String updateMembers(@RequestBody List<MemberUpdateDTO> updateList, HttpSession session) {
		Object loginUser = session.getAttribute("member");

		if (loginUser == null || !"admin".equals(((com.example.outven.entity.Member) loginUser).getUserRole())) {
			return "unauthorized"; // 로그인하지 않았거나, 권한이 없을 때
		}

		memberService.bulkUpdateMembers(updateList);
		return "success";
	}
}
