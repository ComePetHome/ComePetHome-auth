package com.comepethome.user_query.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessResponseMessage {
    SUCCESS(200, ""),
    SUCCESS_LOGOUT(200, "정상 로그아웃 되었습니다");

    private final int code;
    private final String message;
}