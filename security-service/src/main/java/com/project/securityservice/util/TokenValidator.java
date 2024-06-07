package com.project.securityservice.util;

public interface TokenValidator {

    void validateUser(String email, String header);
}
