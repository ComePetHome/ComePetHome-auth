package com.compethome.image.exception.image;


import com.compethome.image.exception.CustomException;
import com.compethome.image.exception.FailResponseMessage;
public class UserAlreadyExistException extends CustomException {
    public UserAlreadyExistException() {
        super(FailResponseMessage.USER_ALREADY_EXIST.getCode(),
                FailResponseMessage.USER_ALREADY_EXIST.getMessage());
    }
}
