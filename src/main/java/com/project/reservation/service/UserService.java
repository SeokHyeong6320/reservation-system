package com.project.reservation.service;

import com.project.reservation.dto.UserDto;
import com.project.reservation.model.input.SignForm;

public interface UserService {
    UserDto register(SignForm.SignUpForm form);
}
