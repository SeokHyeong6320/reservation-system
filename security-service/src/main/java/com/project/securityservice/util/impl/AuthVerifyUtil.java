package com.project.securityservice.util.impl;

import com.project.common.exception.CustomException;
import com.project.securityservice.util.EncryptComponent;
import com.project.securityservice.util.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.project.common.exception.ErrorCode.AUTHENTICATION_NOT_MATCH;

@Service
public class AuthVerifyUtil {

    public void verifyUser(String email) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String emailFromAuth = userDetails.getUsername();

        if(!emailFromAuth.equals(email)) {
            throw new CustomException(AUTHENTICATION_NOT_MATCH);
        }

    }
}
