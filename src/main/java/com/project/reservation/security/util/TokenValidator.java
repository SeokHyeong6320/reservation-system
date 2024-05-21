package com.project.reservation.security.util;

public interface TokenValidator {

    void validateUser(Long pK, String header);
}
