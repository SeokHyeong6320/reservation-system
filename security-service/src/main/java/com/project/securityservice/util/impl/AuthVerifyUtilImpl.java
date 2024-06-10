package com.project.securityservice.util.impl;

import com.project.common.exception.CustomException;
import com.project.securityservice.util.AuthVerityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.project.common.exception.ErrorCode.AUTHENTICATION_NOT_MATCH;

@Component
public class AuthVerifyUtilImpl implements AuthVerityUtil {

    @Override
    public void verifyUser(String email) {

        // security context holder에서 authentication 가져옴
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // 가져온 authentication에서 user email 추출
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailFromAuth = userDetails.getUsername();

        // 해당 엔드포인트의 유저 email과 로그인한 유저의 email이 동일한지 검증
        if(!emailFromAuth.equals(email)) {
            throw new CustomException(AUTHENTICATION_NOT_MATCH);
        }
    }
}
