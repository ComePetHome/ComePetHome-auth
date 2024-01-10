package com.comepethome.user_commend.exception.user;


import com.comepethome.user_commend.exception.CustomException;
import com.comepethome.user_commend.exception.FailResponseMessage;

public class UserNotExistException extends CustomException {
    public UserNotExistException() {
        super(FailResponseMessage.USER_NOT_EXIST.getCode(),
                FailResponseMessage.USER_NOT_EXIST.getMessage());
    }
}
