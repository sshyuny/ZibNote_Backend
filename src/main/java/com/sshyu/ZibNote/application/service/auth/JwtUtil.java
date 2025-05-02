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

    public String generateToken(Long memberId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);
        
        return Jwts.builder()
            .subject(String.valueOf(memberId))
            .expiration(expiry)
            .signWith(randomKey)
            .compact();
    }

    public Long extractMemberId(String token) {
        String memberIdStr = Jwts.parser()
            .verifyWith(randomKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
        return Long.valueOf(memberIdStr);
    }

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
