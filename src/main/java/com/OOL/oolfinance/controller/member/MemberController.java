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
    @GetMapping("/signup")
    public String saveForm() {
        return "redirect:/save.html";
    }

    @PostMapping("/signup")
    public String save(HttpServletRequest request) {
        String id = request.getParameter("providerId");
        String pw = request.getParameter("memberPassword");
        String nickname = request.getParameter("memberNickname");

        MemberDTO memberDTO = new MemberDTO(id, pw, nickname, "form");
        System.out.println("memberDTO = " + memberDTO);
        memberService.signup(memberDTO);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "redirect:/login.html";
    }

    @PostMapping("/api/login")
    public String login(@RequestParam("providerId") String providerId, @RequestParam("password") String password, HttpServletResponse response) {

        MemberDTO memberDTO = new MemberDTO(providerId, password, null, "form");
        Member member = memberService.login(memberDTO);
        if (member != null) {
            //login 성공
            jwtCookieUtils.setJwtCookies(response, member);
            System.out.println("로그인 성공! loginId = " + member.getProviderId());
            return "redirect:/main.html";
        } else {
            // login 실패
            System.out.println("로그인 실패");
            return "redirect:/login?error=true";
        }
    }

    @PostMapping("/api/logout")
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

