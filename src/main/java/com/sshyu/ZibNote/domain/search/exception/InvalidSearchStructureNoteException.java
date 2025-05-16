package com.sshyu.zibnote.domain.search.exception;

import com.sshyu.zibnote.domain.common.exception.InvalidRequestException;

public class InvalidSearchStructureNoteException extends InvalidRequestException {
    
    public InvalidSearchStructureNoteException() {
        super();
    }

    public InvalidSearchStructureNoteException(String message) {
        super(message);
    }

}
