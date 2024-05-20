package com.project.reservation.controller;

import com.project.reservation.dto.UserDto;
import com.project.reservation.model.input.SignForm;
import com.project.reservation.model.response.SignResponse;
import com.project.reservation.model.response.SuccessResponse;
import com.project.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.reservation.model.input.SignForm.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    /**
     * 회원가입 엔드포인트
     * @param form : 회원가입 폼
     * @return : 유저 정보 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<?> userSignUp(
            @RequestBody @Validated SignUpForm form
    ) {

        UserDto userDto = userService.register(form);

        return ResponseEntity.ok(
                SuccessResponse.of(
                        // 보안상 이슈로 비밀번호 등을 제외한 필요한 값만 반환하기 위해 SignResponse 생성
                        SignResponse.SignUp.fromDto(userDto)
                )
        );
    }


}
