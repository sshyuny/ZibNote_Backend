package com.sshyu.zibnotes.domain.search.exception;

import com.sshyu.zibnotes.domain.common.exception.ResourceNotFoundException;

public class SearchStructureNoteNotFoundException extends ResourceNotFoundException {
    
    public SearchStructureNoteNotFoundException() {
        super();
    }

    public SearchStructureNoteNotFoundException(String message) {
        super(message);
    }

}
