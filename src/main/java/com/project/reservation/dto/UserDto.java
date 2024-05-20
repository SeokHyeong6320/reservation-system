package com.project.reservation.dto;

import com.project.reservation.entity.Store;
import com.project.reservation.entity.User;
import com.project.reservation.entity.UserType;
import com.project.reservation.entity.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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



    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .phone(user.getPhone())
                .userType(user.getUserType())
                .registeredDt(user.getRegisteredDt())
                .storeList(user.getStoreList())
                .build();
    }

}