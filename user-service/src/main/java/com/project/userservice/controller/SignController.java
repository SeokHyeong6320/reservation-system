package com.project.userservice.controller;

import com.project.reservation.auth.dto.UserDto;
import com.project.reservation.auth.model.SignForm;
import com.project.reservation.auth.model.SignResponse;
import com.project.reservation.auth.service.SignService;
import com.project.reservation.common.model.SuccessResponse;
import com.project.reservation.security.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;
    private final TokenProvider tokenProvider;


    /**
     * 회원가입 엔드포인트
     * @param form : 회원가입 폼
     * @return : 비밀번호 제외한 유저 정보 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<?> userSignUp(
            @RequestBody @Validated SignForm.SignUpForm form
    ) {

        UserDto userDto = signService.register(form);

        return ResponseEntity.ok(
                SuccessResponse.of(
                // 보안상 이슈로 비밀번호 등을 제외한 필요한 값만 반환하기 위해 SignResponse 생성
                        SignResponse.SignUp.fromDto(userDto)
                )
        );
    }

    /**
     * 로그인 엔드포인트
     * @param form
     * @return 이메일, 유저타입, JWT 반환
     */
    @PostMapping("signin")
    public ResponseEntity<?> userSignIn(
            @RequestBody @Validated SignForm.SignInForm form
    ) {
        UserDto userDto = signService.logIn(form);

        // JWT 반환
        String jwt = tokenProvider.generateToken(
                userDto.getId(),
                userDto.getEmail(),
                // JWT 발행을 위해 userType을 List<String>으로 변환
                new ArrayList<>(List.of(userDto.getUserType().name()))
        );

        return ResponseEntity.ok(
                SuccessResponse.of(
                        SignResponse.SignIn.fromDto(userDto, jwt)
                )
        );
    }




}
