package com.project.compethome.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.ErrorResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        JWTVerificationException jwtVerificationException =
                (JWTVerificationException) request.getAttribute("exception");

        // 토큰 만료의 경우 다른 응답
        if (jwtVerificationException instanceof TokenExpiredException) {
            sendErrorResponse(response, ErrorCode.TOKEN_EXPIRED, 499);
            return;
        }

        // 유효한 토큰이 아닌 경우 다른 응답
        if (jwtVerificationException != null) {
            sendErrorResponse(response, ErrorCode.INVALID_TOKEN, HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 존재 하지 않는 경우 다른 응답
        sendErrorResponse(response, ErrorCode.UNAUTHORIZED, HttpServletResponse.SC_UNAUTHORIZED);
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode, int status)
            throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(errorCode.toString());
    }

}