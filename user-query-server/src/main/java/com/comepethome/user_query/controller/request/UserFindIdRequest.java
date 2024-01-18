package com.comepethome.user_query.controller.request;

import lombok.Data;

@Data
public class UserFindIdRequest {
    private final String name;
    private final String phoneNumber;
}