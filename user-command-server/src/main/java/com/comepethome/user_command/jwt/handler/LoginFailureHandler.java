package com.comepethome.user_command.jwt.handler;

import com.comepethome.user_command.controller.response.UserStatusResponse;
import com.comepethome.user_command.exception.ApiExceptionHandler;
import com.comepethome.user_command.exception.FailResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

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

        UserStatusResponse userStatusResponse = new UserStatusResponse();
        userStatusResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        if (exception instanceof BadCredentialsException) {
            userStatusResponse.setMessage(FailResponseMessage.USER_BAD_CREDENTIAL.getMessage());
            userStatusResponse.setTime(ApiExceptionHandler.getNowDateTime());
            userStatusResponse.setCode(FailResponseMessage.USER_BAD_CREDENTIAL.getCode());
        } else {
            userStatusResponse.setMessage(FailResponseMessage.USER_NOT_AUTHENTICATION.getMessage());
            userStatusResponse.setTime(ApiExceptionHandler.getNowDateTime());
            userStatusResponse.setCode(FailResponseMessage.USER_NOT_AUTHENTICATION.getCode());
        }

        response.getWriter().write(objectMapper.writeValueAsString(userStatusResponse));
    }
}