package com.project.common.util;

import com.project.common.exception.ErrorCode;
import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodeUtil {

    /**
     * FeignException errorMessage에서 errorCode 추출
     */
    public ErrorCode findFromFeignException(FeignException feignException) {

        try {

            String errorMessage = feignException.getMessage();

            // errorCode json 값 추출
            int targetIdx = errorMessage.lastIndexOf("\"errorCode\"");
            int lastIdx = errorMessage.indexOf("\"", targetIdx + 14);
            String errorCode = errorMessage.substring(targetIdx + 13, lastIdx);

            // ErrorCode로 변환
            return ErrorCode.findByString(errorCode);

        } catch (Exception e) {
            // errorCode에 포함되지 않는 exception 처리
            return ErrorCode.FEIGN_SERVER_EXCEPTION;

        }
    }
}
