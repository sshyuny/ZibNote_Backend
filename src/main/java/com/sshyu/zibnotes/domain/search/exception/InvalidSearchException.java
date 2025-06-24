package com.sshyu.zibnotes.domain.search.exception;

import com.sshyu.zibnotes.domain.common.exception.InvalidRequestException;

public class InvalidSearchException extends InvalidRequestException {
    
    public InvalidSearchException() {
        super();
    }

    public InvalidSearchException(String message) {
        super(message);
    }

}
