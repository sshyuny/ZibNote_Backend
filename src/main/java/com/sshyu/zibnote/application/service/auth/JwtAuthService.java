package com.sshyu.zibnote.application.service.auth;

import java.util.UUID;

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

    /**
     * 로그인한 뒤 JWT 토큰을 반환 한다.
     * 
     * 등록된 사용자인지 확인 후, JWT를 발급하여 반환한다.
     * 
     * @param name 로그인할 사용자 이름
     * @return 발급된 JWT 토큰
     */
    @Override
    public Token login(final String name) {

        final Member member = memberUseCase.findByName(name);
        
        final String token = jwtUtil.generateToken(member.getMemberId().toString());
        
        return Token.builder().token(token).build();
    }

    /**
     * 로그아웃한다.
     */
    @Override
    public void logout() {
    }

    /**
     * 로그인한 사용자의 ID를 반환한다.
     * 
     * 로그인시 요청메시지에 넣어두었던 ID를 꺼내서 반환한다.
     * 
     * @return 로그인한 사용자의 ID
     */
    @Override
    public UUID getMemberId() {

        HttpServletRequest request = requestProvider.getObject();
        return (UUID) request.getAttribute("memberId");
    }

}
