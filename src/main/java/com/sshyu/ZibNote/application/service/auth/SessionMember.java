package com.sshyu.zibnote.application.service.auth;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class SessionMember {
    
    private final UUID memberId;

}
