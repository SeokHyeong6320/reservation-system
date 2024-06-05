package com.project.securityservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.UserDto;
import com.project.domain.entity.User;
import com.project.domain.model.SignDomainForm;
import com.project.domain.repository.UserRepository;
import com.project.securityservice.model.UserLoginModel;
import com.project.securityservice.service.SecurityLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.common.exception.ErrorCode.*;
import static com.project.domain.model.SignDomainForm.SignInForm;

@Service
@RequiredArgsConstructor
public class SecurityLonginServiceImpl implements SecurityLoginService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(SignDomainForm.SignUpForm form) {

        // 회원가입 시 중복 이메일 존재 확인
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new CustomException(EMAIL_ALREADY_EXIST);
        }

        User registeredUser = User.register(form);
        String encryptedPwd = passwordEncoder.encode(form.getPassword());
        registeredUser.encryptPassword(encryptedPwd);


        // dto로 변환 해 반환
        return UserDto.fromEntity(
                // UserDto
                userRepository.save(registeredUser)
        );
    }


    @Override
    public UserDto logIn(SignInForm form) {
        User findUser = userRepository
                .findByEmail(form.getEmail())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // passwordEncoder 통해서 password 검증
        if (!passwordEncoder.matches(form.getPassword(), findUser.getPassword())) {
            throw new CustomException(PASSWORD_NOT_MATCH);
        }

//        String jwtToken = jwtTokenProvider.generateToken(
//                findUser.getId(), findUser.getEmail(),
//                new ArrayList<>(List.of(findUser.getUserType().name()))
//        );

        return UserDto.fromEntity(findUser);
    }

    @Override
    public UserDetails loadByEmail(String email) {
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return UserLoginModel.fromEntity(findUser);
    }
}
