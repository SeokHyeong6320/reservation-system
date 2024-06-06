package com.project.userservice.service.impl;

import com.project.domain.dto.UserDto;
import com.project.domain.dto.UserLoginDto;
import com.project.userservice.client.SecurityServiceClient;
import com.project.userservice.model.SignForm;
import com.project.userservice.service.UserSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserSignServiceImpl implements UserSignService {

    private final SecurityServiceClient securityServiceClient;

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

        // FeignClient 사용해서 security-service에서 로그인 처리
        return securityServiceClient.login(form.toDomainForm()).getBody();
    }

}
