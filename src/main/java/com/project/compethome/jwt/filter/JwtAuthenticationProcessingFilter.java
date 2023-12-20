package com.project.compethome.jwt.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.compethome.dto.TokenDTO;
import com.project.compethome.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final static String ACCESS_TOKEN_HEADER_NAME = "Authorization";

    private final static String REFRESH_TOKEN_HEADER_NAME = "Refresh-Token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String refreshTokenWithPrefix = extractRefreshTokenWithPrefix(request);
        String accessTokenWithPrefix = extractAccessTokenWithPrefix(request);

        if (StringUtils.hasText(refreshTokenWithPrefix)) {
            try {
                TokenDTO tokenDto = jwtService.reissue(refreshTokenWithPrefix);
                sendReissueSuccessResponse(response, tokenDto);
                return;
            } catch (JWTVerificationException e) {
                request.setAttribute("exception", e);
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (!StringUtils.hasText(accessTokenWithPrefix)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication
                    = jwtService.resolveAccessTokenWithPrefix(accessTokenWithPrefix);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JWTVerificationException e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    private String extractAccessTokenWithPrefix(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_HEADER_NAME);
    }

    private String extractRefreshTokenWithPrefix(HttpServletRequest request){
       return request.getHeader(REFRESH_TOKEN_HEADER_NAME);
    }

    private void sendReissueSuccessResponse(HttpServletResponse response, TokenDTO tokenDto) {
        response.setHeader(ACCESS_TOKEN_HEADER_NAME, tokenDto.getAccessToken());
        response.setHeader(REFRESH_TOKEN_HEADER_NAME, tokenDto.getRefreshToken());
        response.setStatus(HttpServletResponse.SC_RESET_CONTENT);
    }
}