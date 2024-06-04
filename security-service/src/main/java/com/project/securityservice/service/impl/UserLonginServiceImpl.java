package com.project.securityservice.service.impl;

import com.project.common.exception.CustomException;
import com.project.domain.dto.UserDto;
import com.project.domain.entity.User;
import com.project.domain.repository.UserRepository;
import com.project.securityservice.model.UserLoginForm;
import com.project.securityservice.model.UserLoginModel;
import com.project.securityservice.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.common.exception.ErrorCode.PASSWORD_NOT_MATCH;
import static com.project.common.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserLonginServiceImpl implements UserLoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDto logIn(UserLoginForm form) {
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
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return UserLoginModel.fromEntity(findUser);
    }
}
