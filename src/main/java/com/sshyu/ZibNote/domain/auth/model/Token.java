package com.sshyu.zibnote.domain.auth.model;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class Token {
    
    private final String token;

}
