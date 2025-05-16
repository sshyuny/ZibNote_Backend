package com.sshyu.zibnote.domain.structure.exception;

import com.sshyu.zibnote.domain.common.exception.ResourceNotFoundException;

public class StructureNotFoundException extends ResourceNotFoundException {
    
    public StructureNotFoundException() {
        super();
    }

    public StructureNotFoundException(String message) {
        super(message);
    }
    
}
