package com.comepethome.user_command.exception.user;


import com.comepethome.user_command.exception.CustomException;
import com.comepethome.user_command.exception.FailResponseMessage;

public class UserBadCredentialException extends CustomException {
    public UserBadCredentialException() {
        super(FailResponseMessage.USER_BAD_CREDENTIAL.getCode(),
                FailResponseMessage.USER_BAD_CREDENTIAL.getMessage());
    }
}
