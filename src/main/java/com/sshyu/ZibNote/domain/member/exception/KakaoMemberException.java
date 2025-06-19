package com.sshyu.zibnote.domain.member.exception;

import com.sshyu.zibnote.domain.common.exception.ZibnoteRuntimeException;

public class KakaoMemberException extends ZibnoteRuntimeException {

    public KakaoMemberException() {
        super();
    }

    public KakaoMemberException(String message) {
        super(message);
    }
    
}
