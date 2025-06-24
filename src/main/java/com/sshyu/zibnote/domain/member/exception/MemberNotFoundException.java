package com.sshyu.zibnote.domain.member.exception;

import com.sshyu.zibnote.domain.common.exception.ResourceNotFoundException;

public class MemberNotFoundException extends ResourceNotFoundException {
    
    public MemberNotFoundException() {
        super();
    }
    public MemberNotFoundException(String message) {
        super(message);
    }
    
}
