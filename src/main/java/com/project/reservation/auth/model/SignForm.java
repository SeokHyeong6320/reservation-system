package com.project.reservation.auth.model;

import jakarta.validation.constraints.*;
import lombok.Getter;

public class SignForm {

    @Getter
    public static class SignUpForm {

        @Email
        @NotBlank
        private String email;

        @Size(min = 5, max = 30)
        @NotBlank
        private String password;

        @Size(max = 10)
        @NotBlank
        private String username;

        // 휴대폰 번호는 정규식으로 검증
        @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$")
        private String phone;
    }

    @Getter
    public static class SignInForm {

        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String password;

    }
}
