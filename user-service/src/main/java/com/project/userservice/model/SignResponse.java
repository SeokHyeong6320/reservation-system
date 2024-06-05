package com.project.userservice.model;

import com.project.domain.dto.UserDto;
import com.project.domain.dto.UserLoginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class SignResponse {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class SignUp {

        private final String email;
        private final String username;
        private final String phone;
        private final String userType;
        private final LocalDateTime registeredDt;

        public static SignResponse.SignUp fromDto(UserDto userDto) {
            return SignUp.builder()
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .phone(userDto.getPhone())
                    .userType(userDto.getUserType().name())
                    .registeredDt(userDto.getRegisteredDt())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class SignIn {
        private final String username;
        private final String userType;
        private final String jwt;

        public static SignResponse.SignIn fromDto(UserLoginDto userLoginDto) {
            return SignIn.builder()
                    .username(userLoginDto.getUsername())
                    .userType(userLoginDto.getUserType().name())
                    .jwt(userLoginDto.getJwt())
                    .build();
        }
    }
}
