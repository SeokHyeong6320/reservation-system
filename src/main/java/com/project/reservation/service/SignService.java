package com.project.reservation.service;

import com.project.reservation.dto.UserDto;
import com.project.reservation.model.input.SignForm;
import org.springframework.security.core.userdetails.UserDetails;

public interface SignService {
    UserDto register(SignForm.SignUpForm form);

    UserDto logIn(SignForm.SignInForm form);

    UserDetails loadByEmail(String email);

}
