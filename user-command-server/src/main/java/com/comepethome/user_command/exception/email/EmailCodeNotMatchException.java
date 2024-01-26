package com.comepethome.user_command.exception.email;

import com.comepethome.user_command.exception.CustomException;
import com.comepethome.user_command.exception.FailResponseMessage;

public class EmailCodeNotMatchException extends CustomException {
    public EmailCodeNotMatchException() {
        super(FailResponseMessage.EMAIL_CODE_NOT_MATCH.getCode(),
                FailResponseMessage.EMAIL_CODE_NOT_MATCH.getMessage());
    }
}