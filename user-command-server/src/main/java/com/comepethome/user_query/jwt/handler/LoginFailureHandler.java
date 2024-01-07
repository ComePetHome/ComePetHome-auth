package com.comepethome.user_query.jwt.handler;

import com.comepethome.user_query.controller.response.UserJoinResponse;
import com.comepethome.user_query.exception.FailResponseMessage;
import com.comepethome.user_query.exception.user.UserAuthenticationFailedException;
import com.comepethome.user_query.exception.user.UserPasswordNotMatchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.ErrorResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String message = "";
        int code = 0;
        if (exception instanceof BadCredentialsException) {
            message = FailResponseMessage.USER_PASSWORD_NOT_MATCH.getMessage();
            code = FailResponseMessage.USER_PASSWORD_NOT_MATCH.getCode();
        } else {
            message = FailResponseMessage.USER_NOT_AUTHENTICATION.getMessage();
            code = FailResponseMessage.USER_NOT_AUTHENTICATION.getCode();
        }

        String jsonResponse = objectMapper.writeValueAsString(new UserJoinResponse(151, message));
        response.getWriter().write(jsonResponse);
    }
}