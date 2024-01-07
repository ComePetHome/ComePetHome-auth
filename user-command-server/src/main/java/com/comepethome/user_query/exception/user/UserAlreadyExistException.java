package com.comepethome.user_query.exception.user;

import com.comepethome.user_query.exception.CustomException;
import com.comepethome.user_query.exception.FailResponseMessage;

public class UserAlreadyExistException extends CustomException {
    public UserAlreadyExistException() {
        super(FailResponseMessage.USER_ALREADY_EXIST.getCode(),
                FailResponseMessage.USER_ALREADY_EXIST.getMessage());
    }
}
