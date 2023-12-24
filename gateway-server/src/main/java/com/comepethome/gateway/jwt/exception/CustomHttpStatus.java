package com.comepethome.gateway.jwt.exception;


import lombok.Getter;

@Getter
public enum CustomHttpStatus {
    OK(200),
    UNAUTHORIZED(401),
    EXPIRED(499);

    private final int code;

    CustomHttpStatus(int code) {
        this.code = code;
    }
}





