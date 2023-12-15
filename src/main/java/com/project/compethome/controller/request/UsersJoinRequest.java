package com.project.compethome.controller.request;

import lombok.Data;

@Data
public class UsersJoinRequest {
    private final String userId;
    private final String password;
    private final String name;
    private final String phoneNumber;
}
