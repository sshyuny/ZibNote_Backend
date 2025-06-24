package com.sshyu.zibnote.application.service.auth;

import java.util.UUID;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.auth.model.Token;
import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.in.MemberUseCase;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SessionAuthService implements AuthUseCase {

    private final ObjectProvider<HttpSession> httpSessionProvider;
    private final MemberUseCase memberUseCase;

    @Override
    public Token login(final String name) {

        final Member member = memberUseCase.findByName(name);
        
        HttpSession httpSession = httpSessionProvider.getObject();
        
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, SessionMember.builder()
            .memberId(member.getMemberId())
            .build()
        );

        return Token.builder().build();
    }

    @Override
    public void logout() {
        
        HttpSession httpSession = httpSessionProvider.getObject();
        httpSession.invalidate();
    }

    @Override
    public UUID getMemberId() {

        HttpSession httpSession = httpSessionProvider.getObject();
        final SessionMember sessionMember = (SessionMember) httpSession.getAttribute(SessionConst.LOGIN_MEMBER);

        return sessionMember.getMemberId();
    }

}
