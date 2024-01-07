package com.comepethome.user_query.controller.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class UserJoinResponse {
    private int code;
    private String message;
}
