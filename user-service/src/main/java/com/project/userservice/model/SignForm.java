package com.project.userservice.model;

import com.project.domain.model.SignDomainForm;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        @NotBlank
        @Pattern(regexp = "^01[0-9]-?\\d{3,4}-?\\d{4}$")
        private String phone;

        public SignDomainForm.SignUpForm toDomainForm() {
            return SignDomainForm.SignUpForm.builder()
                    .email(this.email)
                    .password(this.password)
                    .username(this.username)
                    .phone(this.phone)
                    .build();
        }
    }

    @Getter
    public static class SignInForm {

        @Email
        @NotBlank
        private String email;
        @NotBlank
        private String password;

        public SignDomainForm.SignInForm toDomainForm() {
            return SignDomainForm.SignInForm.builder()
                    .email(this.email)
                    .password(this.password)
                    .build();
        }
    }
}
