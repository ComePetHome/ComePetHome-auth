package com.comepethome.gateway.jwt.exception.token;


import com.comepethome.gateway.jwt.exception.CustomException;
import com.comepethome.gateway.jwt.exception.FailResponseMessage;

public class UnexpectedRefreshTokenException extends CustomException {
    public UnexpectedRefreshTokenException() {
        super(FailResponseMessage.TOKEN_UNEXPECTED.getCode(),
                FailResponseMessage.TOKEN_UNEXPECTED.getMessage());
    }
}
