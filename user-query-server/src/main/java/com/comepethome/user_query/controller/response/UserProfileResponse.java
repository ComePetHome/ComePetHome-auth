package com.comepethome.user_query.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.comepethome.user_query.dto.UserDTO;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    public UserProfileResponse(UserDTO userDTO){
        this.userId = userDTO.getUserId();
        this.nickName= userDTO.getNickName();
        this.name= userDTO.getName();
        this.phoneNumber= userDTO.getPhoneNumber();
    }

    private String userId;
    private String nickName;
    private String name;
    private String phoneNumber;
}
