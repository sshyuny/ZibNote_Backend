package com.sshyu.zibnote.domain.note.exception;

import com.sshyu.zibnote.domain.common.exception.ResourceNotFoundException;

public class NoteFieldNotFoundException extends ResourceNotFoundException {

    public NoteFieldNotFoundException() {
        super();
    }
    
    public NoteFieldNotFoundException(String message) {
        super(message);
    }
}
