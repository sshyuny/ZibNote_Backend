package com.sshyu.zibnote.domain.common.exception;

public class UnauthorizedAccessException extends ZibnoteRuntimeException {

    public UnauthorizedAccessException() {
        super();
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }

}
