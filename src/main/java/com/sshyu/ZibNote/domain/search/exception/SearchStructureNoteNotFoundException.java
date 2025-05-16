package com.sshyu.zibnote.domain.search.exception;

import com.sshyu.zibnote.domain.common.exception.ResourceNotFoundException;

public class SearchStructureNoteNotFoundException extends ResourceNotFoundException {
    
    public SearchStructureNoteNotFoundException() {
        super();
    }

    public SearchStructureNoteNotFoundException(String message) {
        super(message);
    }

}
