package com.project.reservation.service;

import com.project.reservation.dto.UserDto;
import com.project.reservation.model.input.SignForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDto register(SignForm.SignUp form);
}
