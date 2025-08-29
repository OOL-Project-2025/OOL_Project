package com.OOL.oolfinance.controller.member;

import com.OOL.oolfinance.dto.MyPageDTO;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.util.CookieUtils;
import com.OOL.oolfinance.util.JwtCookieUtils;
import com.OOL.oolfinance.util.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
	
	//생성자 주입
	private final MemberService memberService;
	private final JwtCookieUtils jwtCookieUtils;


	//회원가입 페이지 출력 요청
	@GetMapping("/member/save")
	public String saveForm() {
		return "redirect:/save.html";
	}
	
	 @PostMapping("/member/save")
	    public String save(HttpServletRequest request) {
	        String id = request.getParameter("memberId");
	        String pw = request.getParameter("memberPassword");
	        String nickname = request.getParameter("memberNickname");

	        MemberDTO memberDTO = new MemberDTO(id, pw, nickname, "form");
	        System.out.println("memberDTO = " + memberDTO);
	        memberService.signup(memberDTO);
	        return "redirect:/member/login";
	    }
	
	@GetMapping("/member/login")
	public String loginForm() {
		return "redirect:/login.html";
	}
	
	@PostMapping("/member/login")
	public String login(@RequestParam("memberId") String memberId, @RequestParam("memberPassword") String memberPassword, HttpServletResponse response) {
		
		MemberDTO memberDTO = new MemberDTO(memberId, memberPassword, null, "form");
		Member member = memberService.login(memberDTO);
		if(member != null) {
			//login 성공
			jwtCookieUtils.setJwtCookies(response, member);
			System.out.println("로그인 성공! loginId = " + member.getProviderId());
			return "redirect:/main.html";
		} else {
			// login 실패
			System.out.println("로그인 실패");
			return "redirect:/member/login?error=true";
		}
	}
		
	@GetMapping ("/member/update")
	@ResponseBody
	@Operation(summary = "유저 정보 가져오기", description = "유저 정보 업데이트하는 API")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "업데이트 성공", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
	})
	public MyPageDTO getUpdateMember(@AuthenticationPrincipal Member member) {
	    return memberService.updateForm(member);
	}	
	
	@PostMapping ("/member/update")
	@Operation(summary = "유저 정보 수정", description = "유저 정보 업데이트하는 API", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
			examples = {
					@ExampleObject(name = "someExample1", value = """ 
                    { 
                        "memberId" : "someValue1", 
                        "memberPassword" : "someValue2", 
                        "memberNickname" : "someValue3"
                    } 
                """)
			}
	)))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "업데이트 성공", content = @Content(mediaType = "application/json")),
			@ApiResponse(responseCode = "400", description = "fail", content = @Content(mediaType = "application/json"))
	})
	public ResponseEntity<String> memberUpdate(@RequestBody MemberDTO memberDTO, @AuthenticationPrincipal Member member) {
	    memberService.memberUpdate(memberDTO, member);
	    return ResponseEntity.ok("업데이트 성공");
	}
	
	@PostMapping("member/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof Member) {
			Member member = (Member) principal;
			log.info("kk");
			log.info(member.getProviderId());
			log.info(member.getProvider());
			log.info(member.getNickname());
			memberService.updateAccessToken(member);
		}

		SecurityContextHolder.clearContext();
        jwtCookieUtils.deleteJwtCookies(request, response);
        return ResponseEntity.ok().build();
    }
}

