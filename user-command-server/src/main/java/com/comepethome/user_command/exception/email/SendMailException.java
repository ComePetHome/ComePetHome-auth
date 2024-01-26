package com.comepethome.user_command.exception.email;

import com.comepethome.user_command.exception.CustomException;
import com.comepethome.user_command.exception.FailResponseMessage;

public class SendMailException extends CustomException {
    public SendMailException() {
        super(FailResponseMessage.EMAIL_SEND_FAIL.getCode(),
                FailResponseMessage.EMAIL_SEND_FAIL.getMessage());
    }
}