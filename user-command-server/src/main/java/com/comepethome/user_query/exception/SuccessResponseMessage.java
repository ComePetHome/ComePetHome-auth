package com.comepethome.user_query.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessResponseMessage {
    USER_JOIN_SUCCESS(200, "회원이 정상 가입되었습니다."),
    USER_LOGIN_SUCCESS(200, "회원이 정상 로그인되었습니다.");

    private final int code;
    private final String message;
}