package com.sshyu.zibnote.adapter.in.web.common.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sshyu.zibnote.adapter.in.web.common.res.ResBodyCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ResBodyForm;
import com.sshyu.zibnote.adapter.in.web.common.res.ResMessageConstants;
import com.sshyu.zibnote.domain.common.exception.InvalidRequestException;
import com.sshyu.zibnote.domain.common.exception.ResourceNotFoundException;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.common.exception.ZibnoteRuntimeException;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(ZibnoteRuntimeException.class)
    public ResponseEntity<ResBodyForm<Void>> handleZibnote(ZibnoteRuntimeException ex) {
        return ResponseEntity.badRequest().body(
            ResBodyForm.of(ResBodyCode.ERROR, ResMessageConstants.ERR_MSG, null)
        );
    }
    
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ResBodyForm<Void>> handleUnauthorized(UnauthorizedAccessException ex) {
        return ResponseEntity.badRequest().body(
            ResBodyForm.of(ResBodyCode.ERROR, ResMessageConstants.ERR_MSG_UNAUTHORIZED, null)
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ResBodyForm<Void>> handleInvalidRequest(InvalidRequestException ex) {
        return ResponseEntity.badRequest().body(
            ResBodyForm.of(ResBodyCode.ERROR, ResMessageConstants.ERR_MSG_INVALID, null)
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResBodyForm<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.badRequest().body(
            ResBodyForm.of(ResBodyCode.ERROR, ResMessageConstants.ERR_MSG_NOT_FOUND, null)
        );
    }

}
