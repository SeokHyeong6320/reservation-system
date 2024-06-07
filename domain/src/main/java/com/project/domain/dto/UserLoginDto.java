package com.project.domain.dto;

import com.project.domain.type.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserLoginDto {

    private String username;
    private UserType userType;
    private String jwt;

    public static UserLoginDto fromEntity(UserDto userDto, String jwt) {
        return UserLoginDto.builder()
                .username(userDto.getUsername())
                .userType(userDto.getUserType())
                .jwt(jwt)
                .build();
    }
}
