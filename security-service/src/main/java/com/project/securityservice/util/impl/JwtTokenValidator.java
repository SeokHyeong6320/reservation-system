package com.project.securityservice.util.impl;

import com.project.common.exception.CustomException;
import com.project.securityservice.util.TokenProvider;
import com.project.securityservice.util.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.project.common.exception.ErrorCode.*;
import static com.project.securityservice.constant.SecurityConst.*;



@Component
@RequiredArgsConstructor
public class JwtTokenValidator implements TokenValidator {

    private final TokenProvider tokenProvider;

    /**
     * token과 id값 가져와서 현재 올바른 회원의 접근인지 체크
     */
    @Override
    public void validateUser(Long id, String header) {

        if (!StringUtils.hasText(header) || !header.startsWith(TOKEN_PREFIX)) {
            throw new CustomException(TOKEN_INVALID);
        }

        // header 에서 "Bearer " 제거
        String jwt = header.substring(TOKEN_PREFIX.length());

        // token에 들어있는 유저의 id 값 얻어옴
        String idByToken = tokenProvider.getPrimaryKey(jwt);

        // 엔드포인트의 id 값과 token의 id 값이 동일한지 확인
        if (!id.toString().equals(idByToken)) {
            throw new CustomException(AUTHENTICATION_NOT_MATCH);
        }

    }
}
