package com.sshyu.zibnote.domain.note.exception;

public class NoteFieldNotFoundException extends RuntimeException {

    public NoteFieldNotFoundException() {
        super();
    }
    
    public NoteFieldNotFoundException(String message) {
        super(message);
    }
}
