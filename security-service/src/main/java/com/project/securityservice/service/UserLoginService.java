package com.project.securityservice.service;

import com.project.domain.dto.UserDto;
import com.project.securityservice.model.UserLoginForm;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserLoginService {

    UserDto logIn(UserLoginForm form);

    UserDetails loadByEmail(String email);
}
