package com.sshyu.zibnote.domain.auth.port.in;

import com.sshyu.zibnote.domain.member.model.Member;

public interface AuthUseCase {
    
    void login(String name);

    void logout();

    String getMemberName();

    Member getMember();
    
}
