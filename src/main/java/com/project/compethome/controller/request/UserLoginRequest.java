package com.project.compethome.controller.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private final String userId;
    private final String password;
}