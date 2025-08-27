package com.OOL.oolfinance.util;

import com.OOL.oolfinance.dto.token.GeneratedToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : JwtTokenProvider
 * @date : 2025. 8. 6. / 오후 10:06
 * @modifyed :
 **/

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    @Value("${custom.jwt.secret}")
    private String secretKey;

    private final long accessTokenValidTime = 1000L * 60 * 60; // 1시간
    private final long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 14; // 14일

    public GeneratedToken generatedToken(String providerId, String provider) {
        String accessToken = createToken(provider, providerId, accessTokenValidTime);
        String refreshToken = createToken(provider, providerId, refreshTokenValidTime);

        return new GeneratedToken(accessToken, refreshToken);
    }

    private String createToken(String provider, String providerId, long validityMillis) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(providerId);

        claims.put("provider", provider);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityMillis))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getProvider(String token) {
        return getClaim(token).get("provider", String.class);
    }

    public String getProviderId(String token) {
        return getClaim(token).getSubject();
    }

    public boolean verifyToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaim(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody(); // 정상 토큰일 경우
        } catch (ExpiredJwtException e) {
            // 만료된 토큰이어도 클레임 꺼낼 수 있음
            return e.getClaims();
        }
    }
}
