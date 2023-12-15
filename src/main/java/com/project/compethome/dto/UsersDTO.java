package com.project.compethome.dto;

import com.project.compethome.controller.request.UsersJoinRequest;
import com.project.compethome.controller.request.UsersLoginRequest;
import com.project.compethome.entity.Users;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UsersDTO {
    private final String userId;
    private final String password;
    private final String name;
    private final String phoneNumber;

    public static UsersDTO translate(Users users){
        return new UsersDTO(users.getUserId(), users.getPassword(), users.getName(), users.getPhoneNumber());
    }

    public static UsersDTO translate(UsersJoinRequest request){
        return new UsersDTO(request.getUserId(), request.getPassword(), request.getName(), request.getPhoneNumber());
    }

    public static UsersDTO translate(UsersLoginRequest request){
        return new UsersDTO(request.getUserId(), request.getPassword(), null, null);
    }
}

