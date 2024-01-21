package com.comepethome.user_query.dto;

import com.comepethome.user_query.controller.request.UserFindIdRequest;
import com.comepethome.user_query.entity.User;
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
        return new UserDTO(user.getUserId(), user.getPassword(),
                            user.getNickName(), user.getCreateAt(), user.getUpdateAt(), user.getName(), user.getPhoneNumber());
    }

    public static UserDTO translate(UserFindIdRequest request){
        return new UserDTO("", "", "", null, null, request.getName(), request.getPhoneNumber());
    }

    public static UserDTO translate(String userid){
        return new UserDTO(userid, "", "", null, null, "", "");
    }
}

