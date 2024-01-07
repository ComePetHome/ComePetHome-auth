package com.comepethome.gateway.jwt.exception.token;


import com.comepethome.gateway.jwt.exception.CustomException;
import com.comepethome.gateway.jwt.exception.FailResponseMessage;

public class TokenExpireException extends CustomException {
    public TokenExpireException() {
        super(FailResponseMessage.TOKEN_EXPIRED.getCode(),
                FailResponseMessage.TOKEN_EXPIRED.getMessage());
    }
}
