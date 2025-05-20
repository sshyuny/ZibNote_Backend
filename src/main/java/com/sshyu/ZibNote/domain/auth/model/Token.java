package com.sshyu.zibnote.domain.auth.model;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class Token {
    
    private final String token;

    public static Token of(final String token) {
        return Token.builder()
            .token(token)
            .build();
    }

}
