package com.comepethome.user_command.controller.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class UserPasswordRequest {
    private final String password;
}
