package com.project.userservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final Environment env;

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 400:
                break;
            case 404:
                if (methodKey.contains("login")) {
                    return new ResponseStatusException(
                            HttpStatus.valueOf(response.status()),
                            env.getProperty("security_service.exception")
                    );
                }
            case 500:
                return null;
            default:
                return new Exception(response.reason());
        }

        return null;
    }
}
