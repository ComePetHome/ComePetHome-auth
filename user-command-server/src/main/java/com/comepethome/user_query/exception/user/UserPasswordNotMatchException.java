package com.comepethome.user_query.exception.user;

import com.comepethome.user_query.exception.CustomException;
import com.comepethome.user_query.exception.FailResponseMessage;

public class UserPasswordNotMatchException extends CustomException {
    public UserPasswordNotMatchException() {
        super(FailResponseMessage.USER_PASSWORD_NOT_MATCH.getCode(),
                FailResponseMessage.USER_PASSWORD_NOT_MATCH.getMessage());
    }
}