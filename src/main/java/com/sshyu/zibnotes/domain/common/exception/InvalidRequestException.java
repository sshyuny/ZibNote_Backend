package com.sshyu.zibnotes.domain.common.exception;

public abstract class InvalidRequestException extends ZibnoteRuntimeException {
    
    public InvalidRequestException() {
        super();
    }

    public InvalidRequestException(String message) {
        super(message);
    }

}
