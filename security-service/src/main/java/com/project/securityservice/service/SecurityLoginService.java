package com.project.securityservice.service;

import com.project.domain.dto.UserDto;
import com.project.domain.dto.UserLoginDto;
import com.project.domain.model.SignDomainForm;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityLoginService {

    UserDto register(SignDomainForm.SignUpForm form);

    UserLoginDto logIn(SignDomainForm.SignInForm form);

    UserDetails loadByEmail(String email);
}
