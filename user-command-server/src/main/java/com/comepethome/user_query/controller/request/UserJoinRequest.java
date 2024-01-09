package com.comepethome.user_query.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


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
