package com.compethome.image.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FailResponseMessage {
    USER_ALREADY_EXIST(150, "동일한 id를 가진 유저가 이미 존재합니다."),
    USER_NOT_EXIST(154, "유저가 존재하지 않습니다."),
    IMAGE_NOT_EXIST(175, "image가 존재하지 않습니다"),
    IMAGE_FILE_NAME_WRONG(176, "파일이름이 옮바르지 않습니다"),
    IMAGE_UPLOAD_FAILED(177, "이미지 업로드에 실패 하였습니다");

    private final int code;
    private final String message;
}