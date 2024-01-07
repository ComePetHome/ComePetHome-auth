package com.comepethome.user_commend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final String time;
    private final int errorCode;

}