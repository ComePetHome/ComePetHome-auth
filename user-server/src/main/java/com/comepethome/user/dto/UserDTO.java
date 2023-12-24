package com.comepethome.user.dto;

import com.comepethome.user.controller.request.UserJoinRequest;
import com.comepethome.user.entity.User;
import com.comepethome.user.controller.request.UserLoginRequest;
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

    public static UserDTO translate(User user){
        return new UserDTO(user.getUserId(), user.getPassword(), user.getAccessToken(), user.getRefreshToken(),
                            user.getNickName(), user.getImageUrl(), user.getCreateAt(), user.getUpdateAt());
    }

    public static UserDTO translate(UserJoinRequest request){
        return new UserDTO(request.getUserId(), request.getPassword(), "", "", request.getNickName(),
                            "", LocalDateTime.now(), LocalDateTime.now());
    }

    public static UserDTO translate(UserLoginRequest request){
        return new UserDTO(request.getUserId(), request.getPassword(), "", "", "", "", null, null);
    }
}

