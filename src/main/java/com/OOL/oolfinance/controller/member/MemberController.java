package com.OOL.oolfinance.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.OOL.oolfinance.dto.MemberDTO;
import com.OOL.oolfinance.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	//생성자 주입
	private final MemberService memberService;
	
	//회원가입 페이지 출력 요청
	@GetMapping("/member/save")
	public String saveForm() {
		return "save";
	}
	
	@PostMapping("/member/save")
	public String save(@ModelAttribute MemberDTO memberDTO) {
		System.out.println("MemberController.save");
		System.out.println("memberDTO = " + memberDTO);
		memberService.signup(memberDTO);
		return "login";
	}
}
