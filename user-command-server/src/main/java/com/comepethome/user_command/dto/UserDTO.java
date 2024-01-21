package com.comepethome.user_command.dto;

import com.comepethome.user_command.controller.request.UserJoinRequest;
import com.comepethome.user_command.controller.request.UserProfileUpdateRequest;
import com.comepethome.user_command.entity.User;
import com.comepethome.user_command.controller.request.UserLoginRequest;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDTO {

    private String userId;
    private String password;
    private String nickName;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private final String name;
    private final String phoneNumber;

    public static UserDTO translate(User user){
        return new UserDTO(user.getUserId(), user.getPassword(), user.getNickName(), user.getCreateAt(), user.getUpdateAt(), user.getName(), user.getPhoneNumber());
    }

    public static UserDTO translate(UserLoginRequest request){
        return new UserDTO(request.getUserId(), request.getPassword(), "", null, null, "", "" );
    }

    public static UserDTO translate(UserProfileUpdateRequest request, String userId){
        return new UserDTO(userId, "", request.getNickName(), null, LocalDateTime.now(), "", request.getPhoneNumber());
    }

    public static UserDTO translate(UserJoinRequest request){
        return new UserDTO(request.getUserId(), request.getPassword(), request.getNickName(), LocalDateTime.now(), LocalDateTime.now(), request.getName(), request.getPhoneNumber());
    }

    public static UserDTO translate(String userId){
        return new UserDTO(userId, "", "", null, null, "", "");
    }

}

