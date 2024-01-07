package com.comepethome.user_query.exception.user;

import com.comepethome.user_query.exception.CustomException;
import com.comepethome.user_query.exception.FailResponseMessage;

public class UserNotExistException extends CustomException {
    public UserNotExistException() {
        super(FailResponseMessage.USER_NOT_EXIST.getCode(),
                FailResponseMessage.USER_NOT_EXIST.getMessage());
    }
}
