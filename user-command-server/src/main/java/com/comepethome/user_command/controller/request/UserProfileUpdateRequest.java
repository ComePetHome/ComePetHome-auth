package com.comepethome.user_command.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserProfileUpdateRequest {
    private String nickName;
    private String phoneNumber;
}
