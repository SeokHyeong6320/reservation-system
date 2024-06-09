package com.project.common.exception;

import com.project.common.model.ErrorResponse;
import com.project.common.util.ErrorCodeUtil;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerAdvice {

    private final ErrorCodeUtil errorCodeUtil;


    // 커스텀 exception 핸들러
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ErrorResponse.of(e));
    }



    // Validator 관련 exception 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException
            (MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(e));

    }

    //openFeign 관련 exception 핸들러
    @ExceptionHandler(feign.FeignException.class)
    public ResponseEntity<?> handleFeignException(FeignException e) {

        ErrorCode errorCode = errorCodeUtil.findFromFeignException(e);
        CustomException customException = new CustomException(errorCode);
        return ResponseEntity
                .status(customException.getErrorCode().getStatus())
                .body(ErrorResponse.of(customException));
    }


    // 그 외 exception 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(e));
    }

}
