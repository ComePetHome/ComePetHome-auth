package com.comepethome.user_command.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPasswordRequest {
    private String password;
}
