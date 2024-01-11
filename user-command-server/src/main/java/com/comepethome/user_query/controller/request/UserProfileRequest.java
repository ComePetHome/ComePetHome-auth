package com.comepethome.user_query.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileRequest {
    private String changeUserId;
    @NotBlank
    private String nickName;
    private String imageUrl;
    @NotBlank
    private String name;
    @NotBlank
    private String phoneNumber;
}
