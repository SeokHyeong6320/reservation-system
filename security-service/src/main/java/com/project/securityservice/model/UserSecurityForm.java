package com.project.securityservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


public class UserSecurityForm {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class LoginForm {
        private String email;

        private String password;
    }


}
