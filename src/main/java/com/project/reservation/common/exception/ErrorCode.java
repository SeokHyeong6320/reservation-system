package com.project.reservation.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
public enum ErrorCode {

    EMAIL_ALREADY_EXIST(BAD_REQUEST, "already exist email"),
    USER_NOT_FOUND(BAD_REQUEST, "couldn't find user"),

    PASSWORD_NOT_MATCH(BAD_REQUEST, "this is wrong password"),
    AUTHENTICATION_NOT_MATCH(BAD_REQUEST, "this is wrong user access"),
    TOKEN_INVALID(BAD_REQUEST, "this is invalid token"),

    PARTNER_ALREADY_ENROLLED(BAD_REQUEST, "this account already partner"),
    PARTNER_NOT_ENROLLED(BAD_REQUEST, "this account is not a partner"),

    GPS_COORDINATE_INVALID(BAD_REQUEST, "this is invalid gps coordinate"),

    ENCRYPT_FAIL(INTERNAL_SERVER_ERROR, "couldn't encrypt"),
    DECRYPT_FAIL(INTERNAL_SERVER_ERROR, "couldn't decrypt"),
    CIPHER_GENERATING_FAIL(INTERNAL_SERVER_ERROR, "couldn't generate cipher");;


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
