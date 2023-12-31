package com.comepethome.user_commend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessResponseMessage {
    SUCCESS(1, "");

    private final int code;
    private final String message;
}