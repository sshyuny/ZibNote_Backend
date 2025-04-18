package com.sshyu.zibnote.domain.auth.port.in;

public interface AuthUseCase {
    
    void login(String name);

    void logout();

    String getMemberName();
    
}
