package com.comepethome.user_command.exception.user;

import com.comepethome.user_command.exception.CustomException;
import com.comepethome.user_command.exception.FailResponseMessage;

public class UserAlreadyExistException extends CustomException {
    public UserAlreadyExistException() {
        super(FailResponseMessage.USER_ALREADY_EXIST.getCode(),
                FailResponseMessage.USER_ALREADY_EXIST.getMessage());
    }
}
