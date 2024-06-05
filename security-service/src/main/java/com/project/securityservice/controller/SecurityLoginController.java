package com.project.securityservice.controller;

import com.project.domain.dto.UserDto;
import com.project.domain.dto.UserLoginDto;
import com.project.securityservice.service.SecurityLoginService;
import com.project.securityservice.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.project.domain.model.SignDomainForm.SignInForm;
import static com.project.domain.model.SignDomainForm.SignUpForm;

@RestController
@RequestMapping("/security-service")
@RequiredArgsConstructor
public class SecurityLoginController {

    private final SecurityLoginService securityLoginService;
    private final TokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<UserDto> userRegister(@RequestBody SignUpForm form) {

        return ResponseEntity.ok(securityLoginService.register(form));
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody SignInForm form) {

        UserDto userDto = securityLoginService.logIn(form);
        String jwtToken = tokenProvider.generateToken(
                userDto.getId(), userDto.getEmail(),
                new ArrayList<>(List.of(userDto.getUserType().name()))
        );

        return ResponseEntity.ok(UserLoginDto.fromUserDto(userDto, jwtToken));
    }
}
