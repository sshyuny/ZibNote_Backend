package com.sshyu.zibnote.domain.auth.port.in;

import java.util.UUID;

import com.sshyu.zibnote.domain.auth.model.Token;

public interface AuthUseCase {
    
    Token login(String name);

    void logout();

    UUID getMemberId();

}
