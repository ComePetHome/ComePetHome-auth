package com.compethome.image.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessResponseMessage {
    SUCCESS(200, "정상"),
    IMAGE_SAVE_SUCCESS(200, "이미지가 정상 저장 되었습니다"),
    IMAGE_UPDATE_SUCCESS(200, "이미지가 정상 수정 되었습니다"),
    IMAGE_DELETE_SUCCESS(200, "이미지가 정상 삭제 되었습니다");
    private final int code;
    private final String message;
}