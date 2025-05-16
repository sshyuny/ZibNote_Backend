package com.sshyu.zibnote.domain.search.exception;

import com.sshyu.zibnote.domain.common.exception.ResourceNotFoundException;

public class SearchNotFoundException extends ResourceNotFoundException {
    
    public SearchNotFoundException() {
        super();
    }

    public SearchNotFoundException(String message) {
        super(message);
    }

}
