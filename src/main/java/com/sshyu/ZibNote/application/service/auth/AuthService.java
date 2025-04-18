package com.sshyu.zibnote.application.service.auth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.auth.port.in.AuthUseCase;
import com.sshyu.zibnote.domain.member.model.Member;
import com.sshyu.zibnote.domain.member.port.in.MemberUseCase;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService implements AuthUseCase {

    private final ObjectProvider<HttpSession> httpSessionProvider;
    private final MemberUseCase memberUseCase;

    @Override
    public void login(String name) {

        memberUseCase.findByName(name);
        
        HttpSession httpSession = httpSessionProvider.getObject();
        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, name);
    }

    @Override
    public void logout() {
        
        HttpSession httpSession = httpSessionProvider.getObject();
        httpSession.invalidate();
    }

    @Override
    public String getMemberName() {

        HttpSession httpSession = httpSessionProvider.getObject();
        return (String) httpSession.getAttribute(SessionConst.LOGIN_MEMBER);
    }

    @Override
    public Member getMember() {

        HttpSession httpSession = httpSessionProvider.getObject();
        String memberName = (String) httpSession.getAttribute(SessionConst.LOGIN_MEMBER);
        
        return memberUseCase.findByName(memberName);
    }

}
