package com.comepethome.user_commend.dto;

import com.comepethome.user_commend.controller.request.UserFindIdRequest;
import com.comepethome.user_commend.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDTO {

    private String userId;
    private String password;
    private String accessToken;
    private String refreshToken;
    private String nickName;
    private String imageUrl;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private final String name;
    private final String phoneNumber;

    public static UserDTO translate(User user){
        return new UserDTO(user.getUser_id(), user.getPassword(), user.getAccess_token(), user.getRefresh_token(),
                            user.getNick_name(), user.getImage_url(), user.getCreate_at(), user.getUpdate_at(), user.getName(), user.getPhone_number());
    }

    public static UserDTO translate(UserFindIdRequest request){
        return new UserDTO("", "", "", "", "", "", null, null, request.getName(), request.getPhoneNumber());
    }
}

