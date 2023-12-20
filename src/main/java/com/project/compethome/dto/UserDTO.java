package com.project.compethome.dto;

import com.project.compethome.controller.request.UserJoinRequest;
import com.project.compethome.controller.request.UserLoginRequest;
import com.project.compethome.entity.User;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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

