package com.comepethome.user_command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<Object> customExceptin(CustomException e){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                getNowDateTime(),
                e.getCode()
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> exception(Exception e){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                getNowDateTime(),
                -1
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    public static String getNowDateTime(){
        ZonedDateTime seoulDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return seoulDateTime.format(formatter);
    }
}