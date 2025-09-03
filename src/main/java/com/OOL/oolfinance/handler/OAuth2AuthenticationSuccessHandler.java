package com.OOL.oolfinance.handler;

import static com.OOL.oolfinance.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.OOL.oolfinance.HttpCookieOAuth2AuthorizationRequestRepository.MODE_PARAM_COOKIE_NAME;

import java.io.IOException;
import java.util.Optional;

import com.OOL.oolfinance.dto.token.GeneratedToken;
import com.OOL.oolfinance.entity.member.Member;
import com.OOL.oolfinance.enums.MemberStatus;
import com.OOL.oolfinance.repository.member.MemberRepository;
import com.OOL.oolfinance.service.member.MemberService;
import com.OOL.oolfinance.util.JwtCookieUtils;
import com.OOL.oolfinance.util.JwtTokenUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.OOL.oolfinance.HttpCookieOAuth2AuthorizationRequestRepository;
import com.OOL.oolfinance.oauth2user.OAuth2Provider;
import com.OOL.oolfinance.oauth2user.OAuth2UserUnlinkManager;
import com.OOL.oolfinance.service.oauth2.OAuth2UserPrincipal;
import com.OOL.oolfinance.util.CookieUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2UserUnlinkManager oAuth2UserUnlinkManager;
    private final MemberRepository memberRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final JwtCookieUtils jwtCookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {

        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String mode = CookieUtils.getCookie(request, MODE_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("");

        OAuth2UserPrincipal principal = getOAuth2UserPrincipal(authentication);
        if (principal == null) {
            return buildErrorUrl(targetUrl, "Login failed");
        }

//        Optional<Member> memberOptional = memberRepository.findByProviderAndProviderId(principal.getUserInfo().getProvider().getRegistrationId(), principal.getUserInfo().getId());
        Member member = memberRepository.findByProviderAndProviderId(principal.getUserInfo().getProvider().getRegistrationId(), principal.getUserInfo().getId())
                .orElseGet(() -> {
                    // 신규 회원 등록
                    Member newMember = Member.builder()
                            .password(null)
                            .nickname(principal.getUserInfo().getNickname())
                            .provider(principal.getUserInfo().getProvider().getRegistrationId())
                            .providerId(principal.getUserInfo().getId())
                            .status(MemberStatus.ACTIVE)
                            .oauth2AccessToken(principal.getUserInfo().getAccessToken())
                            .build();
                    return memberRepository.save(newMember);
                });

        if (member.getStatus().equals(MemberStatus.DEACTIVATED)) {
            OAuth2Provider provider = principal.getUserInfo().getProvider();
            oAuth2UserUnlinkManager.unlink(provider, principal.getUserInfo().getAccessToken());
            log.info("login failed");
            return buildErrorUrl(targetUrl, "Login failed");
        }

        if ("login".equalsIgnoreCase(mode)) {
            log.info("email={}, name={}, nickname={}, accessToken={}",
                    principal.getUserInfo().getEmail(),
                    principal.getUserInfo().getName(),
                    principal.getUserInfo().getNickname(),
                    principal.getUserInfo().getAccessToken());

            GeneratedToken generatedToken = jwtTokenUtils.generatedToken(member.getProviderId(), member.getProvider());
            member.updateRefreshToken(generatedToken.getRefreshToken());
            member.updateOauth2AccessToken(principal.getUserInfo().getAccessToken());
            memberRepository.save(member);

            jwtCookieUtils.setJwtCookies(response, member);
            return UriComponentsBuilder.fromUriString(targetUrl)
                    .build().toUriString();

        }

        return buildErrorUrl(targetUrl, "Login failed");
    }

    private String buildErrorUrl(String targetUrl, String errorMessage) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", errorMessage)
                .build().toUriString();
    }

    private OAuth2UserPrincipal getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        return (principal instanceof OAuth2UserPrincipal) ? (OAuth2UserPrincipal) principal : null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
