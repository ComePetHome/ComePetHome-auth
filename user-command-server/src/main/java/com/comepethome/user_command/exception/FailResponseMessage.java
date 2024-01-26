package com.comepethome.user_command.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FailResponseMessage {
    USER_ALREADY_EXIST(150, "동일한 id를 가진 유저가 이미 존재합니다."),
    USER_BAD_CREDENTIAL(151, "아이디 혹은 비밀번호를 잘못 입력하였습니다"),
    USER_NOT_AUTHENTICATION(153, "인증이 되지 않습니다."),
    USER_NOT_EXIST(154, "유저가 존재하지 않습니다."),

    EMAIL_SEND_FAIL(180, "이메일 전송에 실패 했습니다."),
    EMAIL_CODE_CREATE_FAIL(182, "이메일 코드 만들기에 실패하였습니다."),
    EMAIL_CODE_NOT_MATCH(183, "이메일 코드가 맞지 않습니다 ");

    private final int code;
    private final String message;
}