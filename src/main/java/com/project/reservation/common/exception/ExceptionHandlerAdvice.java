package com.project.reservation.common.exception;

import com.project.reservation.common.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {


    // 커스텀 exception 핸들러
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorResponse.of(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(e));
    }

}
