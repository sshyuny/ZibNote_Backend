package com.sshyu.zibnote.domain.search.exception;

import com.sshyu.zibnote.domain.common.exception.InvalidRequestException;

public class InvalidSearchException extends InvalidRequestException {
    
    public InvalidSearchException() {
        super();
    }

    public InvalidSearchException(String message) {
        super(message);
    }

}
