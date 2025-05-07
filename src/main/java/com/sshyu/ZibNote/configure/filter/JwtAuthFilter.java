package com.sshyu.zibnote.configure.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sshyu.zibnote.application.service.auth.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 없습니다.");
            return;
        }
        
        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "잘못된 접근입니다.");
            return;
        }
        
        Long memberId = jwtUtil.extractMemberId(token);
        request.setAttribute("memberId", memberId);

        filterChain.doFilter(request, response);
    }

}
