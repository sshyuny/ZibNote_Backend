package com.sshyu.zibnotes.domain.member.exception;

import com.sshyu.zibnotes.domain.common.exception.ZibnoteRuntimeException;

public class SocialLoginException extends ZibnoteRuntimeException {

    public SocialLoginException() {
        super();
    }

    public SocialLoginException(String message) {
        super(message);
    }
    
}
