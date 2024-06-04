package com.project.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class SignDomainForm {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class SignUpForm {

        private String email;

        private String password;

        private String username;

        private String phone;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class SignInForm {

        private String email;
        private String password;

    }
}
