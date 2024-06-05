package com.project.domain.dto;

import com.project.domain.entity.Store;
import com.project.domain.entity.User;
import com.project.domain.type.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    private String email;

    private String password;

    private String username;

    private String phone;

    private UserType userType;

    private LocalDateTime registeredDt;

    List<Store> storeList = new ArrayList<>();

    public UserDto(String username) {
        this.username = username;
    }

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .registeredDt(user.getRegisteredDt())
                .build();
    }

}