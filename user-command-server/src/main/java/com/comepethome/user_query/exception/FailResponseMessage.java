package com.comepethome.user_query.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FailResponseMessage {
    USER_ALREADY_EXIST(150, "동일한 id를 가진 유저가 이미 존재합니다."),
    USER_PASSWORD_NOT_MATCH(151, "패스워드가 일치하지 않습니다."),
    USER_NOT_EXIST(152, "회원이 존재하지 않습니다."),
    USER_NOT_AUTHENTICATION(153, "인증이 되지 않습니다.");

    private final int code;
    private final String message;
}