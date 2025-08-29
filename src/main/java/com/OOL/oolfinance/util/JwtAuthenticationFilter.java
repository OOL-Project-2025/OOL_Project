package com.OOL.oolfinance.util;

import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.repository.member.MemberRepository;
import com.OOL.oolfinance.service.member.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : JwtAuthenticationFilter
 * @date : 2025. 8. 13. / 오후 4:51
 * @modifyed :
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final MemberRepository memberRepository;
    private final JwtCookieUtils jwtCookieUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. 쿠키에서 access_token 읽기
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        // 2. 토큰 유효성 확인
        if (token == null) {
            filterChain.doFilter(request, response);
            return; // 토큰 자체가 없는 경우
        }

        String providerId = jwtTokenUtils.getProviderId(token);
        String provider = jwtTokenUtils.getProvider(token);

// 1. Access Token 유효한 경우
        if (jwtTokenUtils.verifyToken(token)) {
            log.info("유효");
            memberRepository.findByProviderAndProviderId(provider, providerId)
                    .ifPresent(member -> {
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(member, null, List.of());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    });
            filterChain.doFilter(request, response);
            return;
        }

// 2. Access Token 만료된 경우
        Member member = memberRepository.findByProviderAndProviderId(provider, providerId)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저"));

// Refresh Token이 아직 유효하다면 Access Token 재발급
        if (jwtTokenUtils.verifyToken(member.getRefreshToken())) {
            jwtCookieUtils.setJwtCookies(response, member);
            log.info("재발급");
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(member, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            jwtCookieUtils.deleteJwtCookies(request, response);
            log.info("로그아웃");
        }
        filterChain.doFilter(request, response);
    }
}
