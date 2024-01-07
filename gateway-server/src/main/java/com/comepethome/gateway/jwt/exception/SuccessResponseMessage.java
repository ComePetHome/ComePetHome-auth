package com.comepethome.gateway.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessResponseMessage {
    SUCCESS(200, "정상");

    private final int code;
    private final String message;
}