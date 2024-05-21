package com.project.reservation.auth.service;

import com.project.reservation.auth.model.SignForm;
import com.project.reservation.auth.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface SignService {
    UserDto register(SignForm.SignUpForm form);

    UserDto logIn(SignForm.SignInForm form);

    UserDetails loadByEmail(String email);

}
