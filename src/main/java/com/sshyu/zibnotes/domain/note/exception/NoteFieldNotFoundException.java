package com.sshyu.zibnotes.domain.note.exception;

import com.sshyu.zibnotes.domain.common.exception.ResourceNotFoundException;

public class NoteFieldNotFoundException extends ResourceNotFoundException {

    public NoteFieldNotFoundException() {
        super();
    }
    
    public NoteFieldNotFoundException(String message) {
        super(message);
    }
}
