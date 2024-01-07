package com.comepethome.user_query.exception.user;


import com.comepethome.user_query.exception.CustomException;
import com.comepethome.user_query.exception.FailResponseMessage;

public class UserBadCredentialException extends CustomException {
    public UserBadCredentialException() {
        super(FailResponseMessage.USER_BAD_CREDENTIAL.getCode(),
                FailResponseMessage.USER_BAD_CREDENTIAL.getMessage());
    }
}
