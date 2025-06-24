package com.sshyu.zibnotes.domain.auth.port.in;

import java.util.UUID;

import com.sshyu.zibnotes.domain.auth.model.Token;

public interface AuthUseCase {
    
    Token login(String name);

    void logout();

    UUID getMemberId();

}
