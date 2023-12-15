package com.project.compethome.exception;

import com.project.compethome.exception.users.UserAlreadyExistException;
import com.project.compethome.exception.users.UserNotExistException;
import com.project.compethome.exception.users.UserPasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException e){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                ResponseMessage.USER_ALREADY_EXIST,
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {UserPasswordNotMatchException.class})
    public ResponseEntity<Object> handleUserPasswordNotMatchException(UserPasswordNotMatchException e){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                ResponseMessage.USER_PASSWORD_NOT_MATCH,
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {UserNotExistException.class})
    public ResponseEntity<Object> handleUserNotExistException(UserNotExistException e){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                ResponseMessage.USER_NOT_EXIST,
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }
}