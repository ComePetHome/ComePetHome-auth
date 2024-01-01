package com.comepethome.user.jwt.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Map;

public class JsonLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {


    public static final String DEFAULT_USERNAME_KEY = "userId";
    public static final String DEFAULT_PASSWORD_KEY = "password";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/api/user/login", "POST");
    private final ObjectMapper objectMapper;

    public JsonLoginProcessingFilter(ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException, IOException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        if (!request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            throw new AuthenticationServiceException(
                    "Authentication content-type not supported: " + request.getContentType());
        }

        ServletInputStream inputStream = request.getInputStream();
        Map<String, String> usernamePasswordMap = objectMapper.readValue(inputStream, new TypeReference<>() {});

        String userId= usernamePasswordMap.get(DEFAULT_USERNAME_KEY);
        String password = usernamePasswordMap.get(DEFAULT_PASSWORD_KEY);

        if (userId == null || password == null) {
            throw new AuthenticationServiceException("Invalid userId or password");
        }

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(userId, password);

        return getAuthenticationManager().authenticate(authRequest);
    }
}