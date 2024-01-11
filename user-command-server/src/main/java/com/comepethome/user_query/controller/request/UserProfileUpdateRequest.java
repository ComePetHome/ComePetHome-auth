package com.comepethome.user_query.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProfileUpdateRequest {
    private String nickName;
    private String imageUrl;
    private String name;
    private String phoneNumber;
}
