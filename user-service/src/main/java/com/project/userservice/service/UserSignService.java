package com.project.userservice.service;


import com.project.domain.dto.UserDto;
import com.project.domain.dto.UserLoginDto;
import com.project.userservice.model.SignForm;

public interface UserSignService {
    UserDto register(SignForm.SignUpForm form);

    UserLoginDto logIn(SignForm.SignInForm form);


}
