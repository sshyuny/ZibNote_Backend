package com.sshyu.zibnote.domain.common.exception;

public class AlreadyDeletedException extends ZibnoteRuntimeException {
    
    public AlreadyDeletedException() {
        super();
    }

    public AlreadyDeletedException(String message) {
        super(message);
    }
}
