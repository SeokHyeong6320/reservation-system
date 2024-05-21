package com.project.reservation.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "already exist email"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn't find user"),

    PARTNER_ALREADY_ENROLLED(HttpStatus.BAD_REQUEST, "this account already partner"),
    PARTNER_NOT_ENROLLED(HttpStatus.BAD_REQUEST, "this account is not a partner"),

    GPS_COORDINATE_INVALID(HttpStatus.BAD_REQUEST, "this is invalid gps coordinate");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
