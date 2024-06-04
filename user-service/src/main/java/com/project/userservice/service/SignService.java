package com.project.userservice.service;


import com.project.domain.dto.UserDto;
import com.project.userservice.model.SignForm;

public interface SignService {
    UserDto register(SignForm.SignUpForm form);

    UserDto logIn(SignForm.SignInForm form);

    UserDetails loadByEmail(String email);

}
