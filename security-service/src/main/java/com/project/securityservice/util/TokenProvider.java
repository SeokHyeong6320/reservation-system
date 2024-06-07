package com.project.securityservice.util;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface TokenProvider {

    String generateToken(Long pK, String username, List<String> roles);

    boolean validateToken(String token);

    Authentication getAuthentication(String token);

    String getEmail(String token);

}
