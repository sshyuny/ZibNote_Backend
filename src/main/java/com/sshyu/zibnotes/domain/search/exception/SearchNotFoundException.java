package com.sshyu.zibnotes.domain.search.exception;

import com.sshyu.zibnotes.domain.common.exception.ResourceNotFoundException;

public class SearchNotFoundException extends ResourceNotFoundException {
    
    public SearchNotFoundException() {
        super();
    }

    public SearchNotFoundException(String message) {
        super(message);
    }

}
