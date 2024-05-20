package com.project.reservation.service.impl;

import com.project.reservation.dto.UserDto;
import com.project.reservation.entity.User;
import com.project.reservation.exception.CustomException;
import com.project.reservation.exception.ErrorCode;
import com.project.reservation.model.input.SignForm;
import com.project.reservation.repository.UserRepository;
import com.project.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.reservation.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // password encoder는 bean으로 등록해 사용

    // 회원가입 서비스
    @Override
    public UserDto register(SignForm.SignUp form) {

        // 회원가입 시 중복 이메일 존재 확인
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new CustomException(EMAIL_ALREADY_EXIST);
        }

        // dto로 변환 해 반환
        return UserDto.fromEntity(
                userRepository.save(User.register(form, passwordEncoder))
        );
    }
}
