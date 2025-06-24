package com.sshyu.zibnote.domain.search.exception;

import com.sshyu.zibnote.domain.common.exception.InvalidRequestException;

public class InvalidSearchStructureException extends InvalidRequestException {
    
    public InvalidSearchStructureException() {
        super();
    }

    public InvalidSearchStructureException(String message) {
        super(message);
    }

}
