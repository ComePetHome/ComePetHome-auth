package com.comepethome.user_query.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessResponseMessage {
    USER_JOIN_SUCCESS(200, "회원이 정상 가입되었습니다."),
    USER_LOGIN_SUCCESS(200, "회원이 정상 로그인 되었습니다."),
    USER_PATCH_SUCCESS(200, "회원이 정상 업데이트 되었습니다."),
    USER_DELETE_SUCCESS(200, "회원이 정상 탈퇴 되었습니다.");

    private final int code;
    private final String message;
}