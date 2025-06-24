package com.sshyu.zibnotes.domain.common.exception;

public abstract class ResourceNotFoundException extends ZibnoteRuntimeException {
    
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
