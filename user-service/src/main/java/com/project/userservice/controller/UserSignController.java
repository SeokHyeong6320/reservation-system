package com.project.userservice.controller;

import com.project.common.model.SuccessResponse;
import com.project.domain.dto.UserDto;
import com.project.domain.dto.UserLoginDto;
import com.project.userservice.model.SignForm;
import com.project.userservice.model.SignResponse;
import com.project.userservice.service.UserSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserSignController {

    private final UserSignService userSignService;


    /**
     * 회원가입 엔드포인트
     * @param form : 회원가입 폼
     * @return : 비밀번호 제외한 유저 정보 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<?> userSignUp(
            @RequestBody @Validated SignForm.SignUpForm form
    ) {

        UserDto userDto = userSignService.register(form);

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
        /*UserDto userDto = userSignService.logIn(form);

        // JWT 반환
        String jwt = tokenProvider.generateToken(
                userDto.getId(),
                userDto.getEmail(),
                // JWT 발행을 위해 userType을 List<String>으로 변환
                new ArrayList<>(List.of(userDto.getUserType().name()))
        );*/

        UserLoginDto userLoginDto = userSignService.logIn(form);

        return ResponseEntity.ok(
                SuccessResponse.of(
                        SignResponse.SignIn.fromDto(userLoginDto)
                )
        );
    }




}
