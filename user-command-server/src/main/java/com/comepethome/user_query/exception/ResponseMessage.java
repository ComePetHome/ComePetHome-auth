package com.comepethome.user_query.exception;

import lombok.Getter;

@Getter
public class ResponseMessage {
    public static final String USER_JOIN_SUCCESS= "회원이 정상 가입되었습니다.";
    public static final String USER_LOGIN_SUCCESS= "회원이 정상 로그인되었습니다.";

    public static final String USER_ALREADY_EXIST= "동일한 이메일을 가진 유저가 이미 존재합니다.";
    public static final String USER_PASSWORD_NOT_MATCH= "패스워드가 일치하지 않습니다.";
    public static final String USER_NOT_EXIST = "회원이 존재하지 않습니다.";

    public static final String TOKEN_EMPTY_OR_NOT_MATCH= "TOKEN이 비어있거나 맞지 않습니다.";
}