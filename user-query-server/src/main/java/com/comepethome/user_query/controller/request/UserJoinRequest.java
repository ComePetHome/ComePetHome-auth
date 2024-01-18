package com.comepethome.user_query.controller.request;

import lombok.Data;


@Data
public class UserJoinRequest {
    private final String userId;
    private final String password;
    private final String nickName;
    private final String name;
    private final String phoneNumber;
}
