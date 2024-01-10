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
        return new UserDTO(user.getUserId(), user.getPassword(), user.getAccessToken(), user.getRefreshToken(),
                            user.getNickName(), user.getImageUrl(), user.getCreateAt(), user.getUpdateAt(), user.getName(), user.getPhoneNumber());
    }

    public static UserDTO translate(UserFindIdRequest request){
        return new UserDTO("", "", "", "", "", "", null, null, request.getName(), request.getPhoneNumber());
    }

    public static UserDTO translate(String userid){
        return new UserDTO(userid, "", "", "", "", "", null, null, "", "");
    }
}

