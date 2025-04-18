package com.sshyu.zibnote.domain.auth.port.in;

import com.sshyu.zibnote.application.service.auth.SessionMember;

public interface AuthUseCase {
    
    void login(String name);

    void logout();

    SessionMember getSessionMember();

    Long getMemberId();

}
