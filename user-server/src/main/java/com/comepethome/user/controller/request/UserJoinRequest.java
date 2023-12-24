package com.comepethome.user.controller.request;

import lombok.Data;

@Data
public class UserJoinRequest {
    private final String userId;
    private final String password;
    private final String nickName;
}
