package com.sshyu.zibnote.domain.search.exception;

import com.sshyu.zibnote.domain.common.exception.ResourceNotFoundException;

public class SearchStructureNotFoundException extends ResourceNotFoundException {

    public SearchStructureNotFoundException() {
        super();
    }

    public SearchStructureNotFoundException(String message) {
        super(message);
    }

}
