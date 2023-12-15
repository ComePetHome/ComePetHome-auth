package com.project.compethome.controller.request;

import lombok.Data;

@Data
public class UsersLoginRequest {
    private final String userId;
    private final String password;
}