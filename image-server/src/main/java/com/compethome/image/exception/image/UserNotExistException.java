package com.compethome.image.exception.image;


import com.compethome.image.exception.CustomException;
import com.compethome.image.exception.FailResponseMessage;
public class UserNotExistException extends CustomException {
    public UserNotExistException() {
        super(FailResponseMessage.USER_NOT_EXIST.getCode(),
                FailResponseMessage.USER_NOT_EXIST.getMessage());
    }
}
