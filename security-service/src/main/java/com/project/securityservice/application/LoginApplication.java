package com.project.securityservice.application;

import com.project.domain.dto.UserDto;
import com.project.domain.dto.UserLoginDto;
import com.project.domain.model.SignDomainForm;
import com.project.securityservice.service.SecurityLoginService;
import com.project.securityservice.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginApplication {

    private final TokenProvider tokenProvider;
    private final SecurityLoginService securityLoginService;

    public UserDto register(SignDomainForm.SignUpForm form) {
        return securityLoginService.register(form);
    }

    public UserLoginDto logIn(SignDomainForm.SignInForm form) {

        UserDto userDto = securityLoginService.logIn(form);

        // jwt token 생성
        String jwtToken = tokenProvider.generateToken(
                userDto.getId(), userDto.getEmail(),
                new ArrayList<>(List.of(userDto.getUserType().name()))
        );

        return UserLoginDto.fromEntity(userDto, jwtToken);

    }


}
