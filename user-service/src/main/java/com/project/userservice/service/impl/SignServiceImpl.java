package com.project.userservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.UserDto;
import com.project.domain.entity.User;
import com.project.userservice.model.SignForm;
import com.project.domain.repository.UserRepository;
import com.project.userservice.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.common.exception.ErrorCode.*;


@Service
@Transactional
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // password encoder는 bean으로 등록해 사용

    // 회원가입 서비스
    @Override
    public UserDto register(SignForm.SignUpForm form) {

        // 회원가입 시 중복 이메일 존재 확인
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new CustomException(EMAIL_ALREADY_EXIST);
        }

        // dto로 변환 해 반환
        return UserDto.fromEntity(
                userRepository.save(User.register(form, passwordEncoder))
        );
    }

    @Override
    public UserDto logIn(SignForm.SignInForm form) {

        User findUser = userRepository
                .findByEmail(form.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // passwordEncoder 통해서 password 검증
        if(!passwordEncoder.matches(form.getPassword(), findUser.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

        return UserDto.fromEntity(findUser);
    }

    @Override
    public UserDetails loadByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
