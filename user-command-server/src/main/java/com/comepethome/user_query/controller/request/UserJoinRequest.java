package com.comepethome.user_query.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserJoinRequest {
    @NotBlank
    private final String userId;
    @NotBlank
    private final String password;
    private final String nickName;
    private final String name;
    @NotBlank
    private final String phoneNumber;
}
