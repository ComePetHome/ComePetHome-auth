package com.comepethome.user_query.controller.response;

import com.comepethome.user_query.dto.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserProfileResponse {

    public UserProfileResponse(UserDTO userDTO){
        this.userId = userDTO.getUserId();
        this.nickName= userDTO.getNickName();
        this.imageUrl= userDTO.getImageUrl();
        this.name= userDTO.getName();
        this.phoneNumber= userDTO.getPhoneNumber();
    }

    private String userId;
    private String nickName;
    private String imageUrl;
    private String name;
    private String phoneNumber;
}
