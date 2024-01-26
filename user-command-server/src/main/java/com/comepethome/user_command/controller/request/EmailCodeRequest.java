package com.comepethome.user_command.controller.request;

import lombok.Data;

@Data
public class EmailCodeRequest {
    private String userId;
    private String code;
}
