package com.comepethome.gateway.jwt.exception.token;

import com.comepethome.gateway.jwt.exception.CustomException;
import com.comepethome.gateway.jwt.exception.FailResponseMessage;

public class TokenUserAlreadyExistException extends CustomException {
    public TokenUserAlreadyExistException() {
        super(FailResponseMessage.TOKEN_USER_ALREADY_EXIST.getCode(),
                FailResponseMessage.TOKEN_USER_ALREADY_EXIST.getMessage());
    }
}
