package com.OOL.oolfinance.util;

import com.OOL.oolfinance.dto.token.GeneratedToken;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.repository.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : JwtCookieUtils
 * @date : 2025. 8. 13. / 오후 4:36
 * @modifyed :
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtCookieUtils {
    private final JwtTokenUtils jwtTokenUtils;
    private final MemberRepository memberRepository;

    public void setJwtCookies(HttpServletResponse response, Member member) {
        GeneratedToken token = jwtTokenUtils.generatedToken(member.getProviderId(), member.getProvider());
        member.updateAccessToken(token.getAccessToken());
        memberRepository.save(member);

        // AccessToken 쿠키
        CookieUtils.addCookie(response, "access_token", token.getAccessToken(), 60 * 60); // 60분
    }

    public void deleteJwtCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "access_token");
    }
}
