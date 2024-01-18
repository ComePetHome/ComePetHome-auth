package com.comepethome.user_command.jwt.handler;

import com.comepethome.user_command.controller.response.UserStatusResponse;
import com.comepethome.user_command.exception.ApiExceptionHandler;
import com.comepethome.user_command.exception.SuccessResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    private final static String AUTH_ID = "Auth-Id";
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        ObjectNode enumAsJson = objectMapper.createObjectNode();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(AUTH_ID, userId);

        response.getWriter().write(objectMapper.writeValueAsString(new UserStatusResponse(
                SuccessResponseMessage.USER_LOGIN_SUCCESS.getMessage(),
                HttpStatus.OK,
                ApiExceptionHandler.getNowDateTime(),
                SuccessResponseMessage.USER_LOGIN_SUCCESS.getCode()
        )));
    }
}