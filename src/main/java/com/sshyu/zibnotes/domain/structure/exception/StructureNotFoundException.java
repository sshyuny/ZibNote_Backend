package com.sshyu.zibnotes.domain.structure.exception;

import com.sshyu.zibnotes.domain.common.exception.ResourceNotFoundException;

public class StructureNotFoundException extends ResourceNotFoundException {
    
    public StructureNotFoundException() {
        super();
    }

    public StructureNotFoundException(String message) {
        super(message);
    }
    
}
