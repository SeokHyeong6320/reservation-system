package com.project.common.util.impl;

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

            int targetIdx = errorMessage.lastIndexOf("\"errorCode\"");
            int lastIdx = errorMessage.indexOf("\"", targetIdx + 14);

            String errorCode = errorMessage.substring(targetIdx + 13, lastIdx);

            return ErrorCode.findByString(errorCode);

        } catch (Exception e) {
            // errorCode에 포함되지 않는 exception 처리
            return ErrorCode.FEIGN_SERVER_EXCEPTION;

        }
    }
}
