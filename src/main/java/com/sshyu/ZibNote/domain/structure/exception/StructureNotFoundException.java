package com.sshyu.zibnote.domain.structure.exception;

public class StructureNotFoundException extends RuntimeException {
    
    public StructureNotFoundException() {
        super();
    }

    public StructureNotFoundException(String message) {
        super(message);
    }
    
}
