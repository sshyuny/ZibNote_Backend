package com.sshyu.zibnote.adapter.in.web.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sshyu.zibnote.adapter.in.web.common.res.ResponseCode;
import com.sshyu.zibnote.adapter.in.web.common.res.ApiResponse;
import com.sshyu.zibnote.adapter.in.web.common.res.ResponseMessage;
import com.sshyu.zibnote.domain.common.exception.AlreadyDeletedException;
import com.sshyu.zibnote.domain.common.exception.InvalidRequestException;
import com.sshyu.zibnote.domain.common.exception.ResourceNotFoundException;
import com.sshyu.zibnote.domain.common.exception.UnauthorizedAccessException;
import com.sshyu.zibnote.domain.common.exception.ZibnoteRuntimeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("Unhandled exception = ", ex);
        return ResponseEntity.internalServerError().body(
            ApiResponse.of(ResponseCode.ERROR, ResponseMessage.ERROR.getMessage(), null)
        );
    }

    @ExceptionHandler(ZibnoteRuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleZibnote(ZibnoteRuntimeException ex) {
        log.error("Unhandled ZibnoteRuntimeException = ", ex);
        return ResponseEntity.badRequest().body(
            ApiResponse.of(ResponseCode.ERROR, ResponseMessage.ERROR.getMessage(), null)
        );
    }
    
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorized(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiResponse.of(ResponseCode.ERROR, ResponseMessage.ERROR_NOT_FOUND.getMessage(), null)
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
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiResponse.of(ResponseCode.ERROR, ResponseMessage.ERROR_NOT_FOUND.getMessage(), null)
        );
    }

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAlreadyDeletedData(AlreadyDeletedException ex) {
        return ResponseEntity.badRequest().body(
            ApiResponse.of(ResponseCode.ERROR, ResponseMessage.ERROR_ALREADY_DELETED.getMessage(), null)
        );
    }

}
