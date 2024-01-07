package com.comepethome.gateway.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FailResponseMessage {
    TOKEN_USER_ALREADY_EXIST(170, "토큰db에 동일한 id를 가진 유저가 이미 존재합니다."),
    TOKEN_USER_NOT_EXIST(171, "토큰db에 회원이 존재하지 않습니다."),
    TOKEN_EXPIRED(172, "토큰이 만료 되었습니다"),
    TOKEN_NOT_VERIFIED(173, "토큰이 증명되지 않습니다"),
    TOKEN_UNEXPECTED(174, "토큰이 비어있거나 맞지 않습니다");

    private final int code;
    private final String message;
}