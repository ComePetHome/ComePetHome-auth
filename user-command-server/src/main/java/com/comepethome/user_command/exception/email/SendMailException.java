package com.comepethome.user_command.exception.email;

import com.comepethome.user_command.exception.CustomException;
import com.comepethome.user_command.exception.FailResponseMessage;

public class SendMailException extends CustomException {
    public SendMailException() {
        super(FailResponseMessage.USER_ALREADY_EXIST.getCode(),
                FailResponseMessage.USER_ALREADY_EXIST.getMessage());
    }
}