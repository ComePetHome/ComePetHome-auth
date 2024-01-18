package com.comepethome.user_command.exception.user;

import com.comepethome.user_command.exception.CustomException;
import com.comepethome.user_command.exception.FailResponseMessage;

public class UserAuthenticationFailedException extends CustomException {
    public UserAuthenticationFailedException() {
        super(FailResponseMessage.USER_NOT_AUTHENTICATION.getCode(),
                FailResponseMessage.USER_NOT_AUTHENTICATION.getMessage());
    }
}