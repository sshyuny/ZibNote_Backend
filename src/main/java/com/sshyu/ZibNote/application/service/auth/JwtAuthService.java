package com.sshyu.zibnote.application.service.auth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.auth.model.Token;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.in.MemberUseCase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class JwtAuthService implements AuthUseCase {

    private final ObjectProvider<HttpServletRequest> requestProvider;
    private final JwtUtil jwtUtil;
    private final MemberUseCase memberUseCase;

    @Override
    public Token login(String name) {

        Member member = memberUseCase.findByName(name);
        
        String token = jwtUtil.generateToken(member.getMemberId());
        
        return Token.builder().token(token).build();
    }

    @Override
    public void logout() {
    }

    @Override
    public Long getMemberId() {
        HttpServletRequest request = requestProvider.getObject();
        return (Long) request.getAttribute("memberId");
    }

}
