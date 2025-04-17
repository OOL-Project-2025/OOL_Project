package com.OOL.oolfinance.controller.member;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.OOL.oolfinance.dto.MemberDTO;
import com.OOL.oolfinance.service.member.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	//생성자 주입
	private final MemberService memberService;
	
	//회원가입 페이지 출력 요청
	@GetMapping("/member/save")
	public String saveForm() {
		return "/save.html";
	}
	
	 @PostMapping("/member/save")
	    public String save(HttpServletRequest request) {
	        String id = request.getParameter("memberId");
	        String pw = request.getParameter("memberPassword");
	        String nickname = request.getParameter("memberNickname");

	        MemberDTO memberDTO = new MemberDTO(id, pw, nickname);
	        System.out.println("memberDTO = " + memberDTO);
	        memberService.signup(memberDTO);
	        return "redirect:/member/login";
	    }
	
	@GetMapping("/member/login")
	public String loginForm() {
		return "redirect:/login.html";
	}
	
	@PostMapping("/member/login")
	public String login(@RequestParam("memberId") String memberId, @RequestParam("memberPassword") String memberPassword, HttpSession session ) {
		
		MemberDTO memberDTO = new MemberDTO(memberId, memberPassword, null);
		MemberDTO loginResult = memberService.login(memberDTO);
		if(loginResult != null) {
			//login 성공
			session.setAttribute("loginId", loginResult.getMemberId());
			 System.out.println("로그인 성공! loginId = " + loginResult.getMemberId());
			return "redirect:/main.html";
		} else {
			// login 실패
			System.out.println("로그인 실패");
			return "redirect:/member/login?error=true";
		}
	}
		
	@GetMapping ("/member/update")
	@ResponseBody
	public MemberDTO getUpdateMember(HttpSession session) {
	    String myId = (String) session.getAttribute("loginId");
	    return memberService.updateForm(myId);
	}	
	
	@PostMapping ("/member/update")
	public ResponseEntity<String> memberUpdate(@RequestBody MemberDTO memberDTO, HttpSession session) {
		String loginId = (String) session.getAttribute("loginId");
	    memberDTO.setMemberId(loginId);
	    memberService.memberUpdate(memberDTO);
	    return ResponseEntity.ok("업데이트 성공");
	}
	
	@PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); // 세션 삭제
        return ResponseEntity.ok().build();
    }
	
	@DeleteMapping("/member/delete/{memberId}")
	public ResponseEntity<?> deleteMember(@PathVariable("memberId") String memberId) {
	    memberService.deleteMember(memberId);
	    return ResponseEntity.ok().build();
	}
}

