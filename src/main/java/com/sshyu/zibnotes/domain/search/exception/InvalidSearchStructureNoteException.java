package com.sshyu.zibnotes.domain.search.exception;

import com.sshyu.zibnotes.domain.common.exception.InvalidRequestException;

public class InvalidSearchStructureNoteException extends InvalidRequestException {
    
    public InvalidSearchStructureNoteException() {
        super();
    }

    public InvalidSearchStructureNoteException(String message) {
        super(message);
    }

}
