package com.comepethome.user_command.exception.email;

import com.comepethome.user_command.exception.CustomException;
import com.comepethome.user_command.exception.FailResponseMessage;

public class CreateCodeException extends CustomException {
    public CreateCodeException() {
        super(FailResponseMessage.EMAIL_CODE_CREATE_FAIL.getCode(),
                FailResponseMessage.EMAIL_CODE_CREATE_FAIL.getMessage());
    }
}