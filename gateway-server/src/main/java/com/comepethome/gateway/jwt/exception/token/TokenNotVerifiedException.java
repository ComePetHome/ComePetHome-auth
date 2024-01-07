package com.comepethome.gateway.jwt.exception.token;


import com.comepethome.gateway.jwt.exception.CustomException;
import com.comepethome.gateway.jwt.exception.FailResponseMessage;

public class TokenNotVerifiedException extends CustomException {
    public TokenNotVerifiedException() {
        super(FailResponseMessage.TOKEN_NOT_VERIFIED.getCode(),
                FailResponseMessage.TOKEN_NOT_VERIFIED.getMessage());
    }
}
