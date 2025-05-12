package com.sshyu.zibnote.application.service.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
    
    @Value("${jwt.expiration}")
    private long validityInMs;

    final static SecretKey randomKey = Jwts.SIG.HS256.key().build();

    /**
     * 사용자 ID로 JWT 토큰을 발급 및 반환한다.
     * 
     * @param memberId 로그인할 ID
     * @return 발급된 JWT 토큰
     */
    public String generateToken(Long memberId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);
        
        return Jwts.builder()
            .subject(String.valueOf(memberId))
            .expiration(expiry)
            .signWith(randomKey)
            .compact();
    }

    /**
     * JWT 토큰에서 사용자 ID를 파싱하여 반환한다.
     * 
     * @throws JwtException 유효하지 않은 토큰일 경우
     * @param token JWT 토큰
     * @return 사용자 ID
     */
    public Long extractMemberId(String token) {
        String memberIdStr = Jwts.parser()
            .verifyWith(randomKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
        return Long.valueOf(memberIdStr);
    }

    /**
     * 유효한 토큰인지 확인한다.
     * 
     * @param token JWT 토큰
     * @return 유효한 토큰 여부
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(randomKey)
                .build()
                .parseSignedClaims(token);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }
    
}
