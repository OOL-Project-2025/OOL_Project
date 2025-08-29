package com.OOL.oolfinance.controller.token;

import com.OOL.oolfinance.dto.general.GeneralResponse;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.enums.StatusEnum;
import com.OOL.oolfinance.repository.member.MemberRepository;
import com.OOL.oolfinance.util.CookieUtils;
import com.OOL.oolfinance.util.JwtCookieUtils;
import com.OOL.oolfinance.util.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : TokenRestController
 * @date : 2025. 8. 13. / 오후 4:55
 * @modifyed :
 **/

@Slf4j
@RequestMapping("/api/token")
@RestController
@RequiredArgsConstructor
public class TokenRestController {

    private final JwtTokenUtils jwtTokenUtils;
    private final JwtCookieUtils jwtCookieUtils;
    private final MemberRepository memberRepository;

    @PostMapping(value = "/refresh")
    @Operation(summary = "토큰 재설정", description = "refreshToken을 이용한 재설정 API")
    public ResponseEntity<GeneralResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String expiredAccessToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) {
                    expiredAccessToken = cookie.getValue();
                    break;
                }
            }
        }
        if (expiredAccessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String providerId = "";
        String provider = "";
        try {
            providerId = jwtTokenUtils.getProviderId(expiredAccessToken);
            provider = jwtTokenUtils.getProvider(expiredAccessToken);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰에서 안전하게 Claims 읽기
            providerId = e.getClaims().getSubject();
            provider = e.getClaims().get("provider", String.class);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Member member = memberRepository.findByProviderAndProviderId(provider, providerId)
                .orElseThrow(() -> new RuntimeException("회원 없음"));

        if (!jwtTokenUtils.verifyToken(member.getRefreshToken())) {
            // RefreshToken 만료 → 로그아웃 처리
            jwtCookieUtils.deleteJwtCookies(request, response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 4️⃣ 새 AccessToken 발급
        jwtCookieUtils.setJwtCookies(response, member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-cookie")
    public String checkCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    return "쿠키 값: " + cookie.getValue();
                }
            }
        }
        return "쿠키를 찾을 수 없습니다.";
    }
}

