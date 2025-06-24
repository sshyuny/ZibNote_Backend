package com.sshyu.zibnotes.domain.search.exception;

import com.sshyu.zibnotes.domain.common.exception.ResourceNotFoundException;

public class SearchStructureNotFoundException extends ResourceNotFoundException {

    public SearchStructureNotFoundException() {
        super();
    }

    public SearchStructureNotFoundException(String message) {
        super(message);
    }

}
