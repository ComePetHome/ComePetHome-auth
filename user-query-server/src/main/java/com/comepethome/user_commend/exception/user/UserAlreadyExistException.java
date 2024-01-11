package com.comepethome.user_commend.exception.user;

import com.comepethome.user_commend.entity.User;
import com.comepethome.user_commend.exception.CustomException;
import com.comepethome.user_commend.exception.FailResponseMessage;

public class UserAlreadyExistException extends CustomException {
    public UserAlreadyExistException() {
        super(FailResponseMessage.USER_ALREADY_EXIST.getCode(),
                FailResponseMessage.USER_ALREADY_EXIST.getMessage());
    }
}
