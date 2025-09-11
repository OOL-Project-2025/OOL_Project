package com.OOL.oolfinance.handler;

import com.OOL.oolfinance.entity.member.CustomUserDetails;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.repository.member.MemberRepository;
import com.OOL.oolfinance.util.CookieUtils;
import com.OOL.oolfinance.util.JwtTokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : jwtHandshakeInterceptor
 * @date : 2025. 9. 10. / 오후 10:25
 * @modifyed :
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenUtils jwtTokenUtils;
    private final MemberRepository memberRepository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("HI");
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpServletResponse httpResponse = ((ServletServerHttpResponse) response).getServletResponse();

            String accessToken = CookieUtils.getCookie(httpRequest, "access_token").map(Cookie::getValue).orElse(null);
            System.out.println("HI");
            log.info(accessToken);
            if (jwtTokenUtils.verifyToken(accessToken)) {
                String providerId = jwtTokenUtils.getProviderId(accessToken);
                String provider = jwtTokenUtils.getProvider(accessToken);

                System.out.println(provider);
                System.out.println(providerId);
                memberRepository.findByProviderAndProviderId(provider, providerId)
                        .ifPresent(member -> {
                            CustomUserDetails userDetails = new CustomUserDetails(member);
                            attributes.put("userDetails", userDetails);
                        });
                return true;
            } else {
                httpResponse.setStatus(401);
                return false;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
