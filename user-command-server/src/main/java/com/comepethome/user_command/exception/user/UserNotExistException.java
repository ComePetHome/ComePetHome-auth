package com.comepethome.user_command.exception.user;


import com.comepethome.user_command.exception.CustomException;
import com.comepethome.user_command.exception.FailResponseMessage;

public class UserNotExistException extends CustomException {
    public UserNotExistException() {
        super(FailResponseMessage.USER_NOT_EXIST.getCode(),
                FailResponseMessage.USER_NOT_EXIST.getMessage());
    }
}
