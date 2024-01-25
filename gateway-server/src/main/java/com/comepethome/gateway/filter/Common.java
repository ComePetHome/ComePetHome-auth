package com.comepethome.gateway.filter;

import com.comepethome.gateway.jwt.JwtService;

import java.util.List;
import java.util.Optional;

public class Common {
    public static final String USER_WITHDRAW_URI = "/api/user/command/withdraw";
    public static final String USER_LOGOUT_URI = "/api/user/query/logout";
    public static final String ACCESS_TOKEN_SUBJECT = "access-token";
    public static final String REFRESH_TOKEN_SUBJECT = "refresh-token";
    public static final String USER_ID= "userId";
    public static final String AUTH_ID = "Auth-Id";
}
