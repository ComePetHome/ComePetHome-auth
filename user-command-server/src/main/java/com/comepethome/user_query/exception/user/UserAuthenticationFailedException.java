package com.comepethome.user_query.exception.user;

import com.comepethome.user_query.exception.CustomException;
import com.comepethome.user_query.exception.FailResponseMessage;

public class UserAuthenticationFailedException extends CustomException {
    public UserAuthenticationFailedException() {
        super(FailResponseMessage.USER_NOT_AUTHENTICATION.getCode(),
                FailResponseMessage.USER_NOT_AUTHENTICATION.getMessage());
    }
}