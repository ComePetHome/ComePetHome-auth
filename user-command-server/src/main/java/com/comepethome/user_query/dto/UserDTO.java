package com.comepethome.user_query.dto;

import com.comepethome.user_query.controller.request.UserJoinRequest;
import com.comepethome.user_query.entity.User;
import com.comepethome.user_query.controller.request.UserLoginRequest;
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

    public static UserDTO translate(UserLoginRequest request){
        return new UserDTO(request.getUserId(), request.getPassword(), "", "", "", "", null, null, "", "");
    }



    public static UserDTO translate(UserJoinRequest request){
        return new UserDTO(request.getUserId(), request.getPassword(), "", "", request.getNickName(),
                "", LocalDateTime.now(), LocalDateTime.now(), request.getName(), request.getPhoneNumber());
    }
}

