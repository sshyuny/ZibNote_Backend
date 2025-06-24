package com.sshyu.zibnotes.domain.common.exception;

public class AlreadyDeletedException extends ZibnoteRuntimeException {
    
    public AlreadyDeletedException() {
        super();
    }

    public AlreadyDeletedException(String message) {
        super(message);
    }
}
