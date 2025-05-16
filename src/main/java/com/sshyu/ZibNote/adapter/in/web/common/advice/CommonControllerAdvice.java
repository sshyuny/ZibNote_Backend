package com.sshyu.zibnote.adapter.in.web.common.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.domain.common.exception.InvalidRequestException;
import com.sshyu.zibnote.domain.common.exception.ResourceNotFoundException;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.common.exception.ZibnoteRuntimeException;

@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(ZibnoteRuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleZibnote(ZibnoteRuntimeException ex) {
        return ResponseEntity.badRequest().body(
            ApiResponse.of(ResponseCode.ERROR, ResponseMessage.ERROR.getMessage(), null)
        );
    }
    
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorized(UnauthorizedAccessException ex) {
        return ResponseEntity.badRequest().body(
            ApiResponse.of(ResponseCode.ERROR, ResponseMessage.ERROR_UNAUTHORIZED.getMessage(), null)
        );
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidRequest(InvalidRequestException ex) {
        return ResponseEntity.badRequest().body(
            ApiResponse.of(ResponseCode.ERROR, ResponseMessage.ERROR_INVALID.getMessage(), null)
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.badRequest().body(
            ApiResponse.of(ResponseCode.ERROR, ResponseMessage.ERROR_NOT_FOUND.getMessage(), null)
        );
    }

}
