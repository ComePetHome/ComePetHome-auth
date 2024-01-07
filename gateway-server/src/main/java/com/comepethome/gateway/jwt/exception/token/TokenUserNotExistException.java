package com.comepethome.gateway.jwt.exception.token;


import com.comepethome.gateway.jwt.exception.CustomException;
import com.comepethome.gateway.jwt.exception.FailResponseMessage;

public class TokenUserNotExistException extends CustomException {
    public TokenUserNotExistException() {
        super(FailResponseMessage.TOKEN_USER_NOT_EXIST.getCode(),
                FailResponseMessage.TOKEN_USER_NOT_EXIST.getMessage());
    }
}
