package com.comepethome.user_commend.exception.user;

import com.comepethome.user_commend.exception.CustomException;
import com.comepethome.user_commend.exception.FailResponseMessage;

public class UserBadCredentialException extends CustomException {
    public UserBadCredentialException() {
        super(FailResponseMessage.USER_BAD_CREDENTIAL.getCode(),
                FailResponseMessage.USER_BAD_CREDENTIAL.getMessage());
    }
}
