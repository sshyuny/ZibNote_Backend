package com.sshyu.zibnote.domain.common.exception;

public abstract class ResourceNotFoundException extends ZibnoteRuntimeException {
    
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
