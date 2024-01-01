package com.comepethome.user_query.jwt.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//    private final JwtService jwtService;

    private final static String AUTH_ID = "Auth-Id";
//    private final static String ACCESS_TOKEN_HEADER_NAME = "Authorization";
//    private final static String REFRESH_TOKEN_HEADER_NAME = "Refresh-Token";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
//        String accessTokenWithPrefix = jwtService.createAccessTokenWithPrefix(authentication);
//        String refreshTokenWithPrefix = jwtService.createRefreshTokenWithPrefix(authentication);
//
//        response.setHeader(ACCESS_TOKEN_HEADER_NAME, accessTokenWithPrefix);
//        response.setHeader(REFRESH_TOKEN_HEADER_NAME, refreshTokenWithPrefix);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(AUTH_ID, userId);


        response.getWriter().write("정상 로그인 되었습니다");
    }
}