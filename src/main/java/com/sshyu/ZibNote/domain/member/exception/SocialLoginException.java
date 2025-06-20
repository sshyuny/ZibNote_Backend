package com.sshyu.zibnote.domain.member.exception;

import com.sshyu.zibnote.domain.common.exception.ZibnoteRuntimeException;

public class SocialLoginException extends ZibnoteRuntimeException {

    public SocialLoginException() {
        super();
    }

    public SocialLoginException(String message) {
        super(message);
    }
    
}
