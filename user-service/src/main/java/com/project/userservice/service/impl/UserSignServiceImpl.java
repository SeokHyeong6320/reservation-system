package com.project.userservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.common.util.impl.ErrorCodeUtil;
import com.project.domain.dto.UserDto;
import com.project.domain.dto.UserLoginDto;
import com.project.domain.entity.User;
import com.project.userservice.client.SecurityServiceClient;
import com.project.userservice.model.SignForm;
import com.project.domain.repository.UserRepository;
import com.project.userservice.service.UserSignService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.common.exception.ErrorCode.*;


@Service
@Transactional
@RequiredArgsConstructor
public class UserSignServiceImpl implements UserSignService {

    private final CircuitBreakerFactory circuitBreakerFactory;
    private final SecurityServiceClient securityServiceClient;
    private final ErrorCodeUtil errorCodeUtil;

    /**
     * 회원가입 서비스
     */
    @Override
    public UserDto register(SignForm.SignUpForm form) {

        // FeignClient 사용해서 security-service에서 회원가입 처리
        return securityServiceClient.userRegister(form.toDomainForm()).getBody();
    }

    @Override
    public UserLoginDto logIn(SignForm.SignInForm form) {


        CircuitBreaker circuitBreaker =
                circuitBreakerFactory.create("circuitbreaker");


        UserLoginDto userLoginDto = circuitBreaker.run(
                () -> securityServiceClient.login(form.toDomainForm()).getBody(),
                throwable -> new UserLoginDto
                        ("Error", null, null)
        );

        return userLoginDto;
    }

}
