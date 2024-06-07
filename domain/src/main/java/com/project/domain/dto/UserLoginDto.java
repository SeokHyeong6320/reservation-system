package com.project.domain.dto;

import com.project.domain.entity.User;
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

    public static UserLoginDto fromEntity(User user, String jwt) {
        return UserLoginDto.builder()
                .username(user.getUsername())
                .userType(user.getUserType())
                .jwt(jwt)
                .build();
    }
}
