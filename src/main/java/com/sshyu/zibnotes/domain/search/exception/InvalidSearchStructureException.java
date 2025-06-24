package com.sshyu.zibnotes.domain.search.exception;

import com.sshyu.zibnotes.domain.common.exception.InvalidRequestException;

public class InvalidSearchStructureException extends InvalidRequestException {
    
    public InvalidSearchStructureException() {
        super();
    }

    public InvalidSearchStructureException(String message) {
        super(message);
    }

}
