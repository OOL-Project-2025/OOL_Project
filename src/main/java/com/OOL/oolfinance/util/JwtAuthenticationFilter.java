package com.OOL.oolfinance.util;

import com.OOL.oolfinance.repository.member.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final MemberRepository memberRepository;

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
        if (token != null && jwtTokenUtils.verifyToken(token)) {
            String providerId = jwtTokenUtils.getProviderId(token);
            String provider = jwtTokenUtils.getProvider(token);

            memberRepository.findByProviderAndProviderId(provider, providerId).ifPresent(member -> {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(member, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        }

        filterChain.doFilter(request, response);
    }
}
