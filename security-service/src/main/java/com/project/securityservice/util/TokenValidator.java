package com.project.securityservice.util;

public interface TokenValidator {

    void validateUser(Long pK, String header);
}
