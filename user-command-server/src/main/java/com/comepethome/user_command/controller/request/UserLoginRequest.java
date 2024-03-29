package com.comepethome.user_command.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotBlank
    private final String userId;
    @NotBlank
    private final String password;
}