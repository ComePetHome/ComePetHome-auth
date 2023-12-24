package com.comepethome.gateway.jwt.exception;

import lombok.Getter;

@Getter
public class ResponseMessage {
    public static final String TOKEN_EMPTY_OR_NOT_MATCH= "TOKEN이 비어있거나 맞지 않습니다.";
    public static final String USER_NOT_EXIST = "회원이 존재하지 않습니다.";
}