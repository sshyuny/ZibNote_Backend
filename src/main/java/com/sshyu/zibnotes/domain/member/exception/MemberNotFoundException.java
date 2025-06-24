package com.sshyu.zibnotes.domain.member.exception;

import com.sshyu.zibnotes.domain.common.exception.ResourceNotFoundException;

public class MemberNotFoundException extends ResourceNotFoundException {
    
    public MemberNotFoundException() {
        super();
    }
    public MemberNotFoundException(String message) {
        super(message);
    }
    
}
