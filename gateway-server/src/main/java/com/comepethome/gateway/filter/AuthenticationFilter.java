package com.comepethome.gateway.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.comepethome.gateway.jwt.JwtService;
import com.comepethome.gateway.jwt.dto.TokenDTO;
import com.comepethome.gateway.jwt.exception.CustomHttpStatus;
import com.comepethome.gateway.jwt.exception.token.UnexpectedRefreshTokenException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final String ACCESS_TOKEN_SUBJECT = "access-token";
    private static final String REFRESH_TOKEN_SUBJECT = "refresh-token";
    @Autowired
    private final JwtService jwtService;

    @Autowired
    public AuthenticationFilter(JwtService jwtService){
        super(AuthenticationFilter.Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (request.getHeaders().containsKey(REFRESH_TOKEN_SUBJECT)) {
                List<String> refreshToken = request.getHeaders().get(REFRESH_TOKEN_SUBJECT);
                ServerHttpResponse response = exchange.getResponse();
                assert refreshToken != null;
                TokenDTO tokenDTO = jwtService.reissue(refreshToken.get(0));

                response.getHeaders().add(ACCESS_TOKEN_SUBJECT, tokenDTO.getAccessToken());
                response.getHeaders().add(REFRESH_TOKEN_SUBJECT, tokenDTO.getRefreshToken());
                return response.setComplete();
            }

            return Optional.ofNullable(request.getHeaders().get(ACCESS_TOKEN_SUBJECT))
                    .map(accessToken -> {
                        CustomHttpStatus code = jwtService.resolveAccessTokenWithPrefix(accessToken.get(0));
                        ServerHttpResponse response = exchange.getResponse();
                        if (code != CustomHttpStatus.OK) {
                            response.setRawStatusCode(code.getCode());
                            String responseBody = "Error occurred: " + code;
                            DataBuffer buffer = response.bufferFactory().wrap(responseBody.getBytes());
                            return response.writeWith(Mono.just(buffer));
                        }
                        return chain.filter(exchange);
                    })
                    .orElseGet(() -> {
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return response.setComplete(); // Return the completion signal as a Mono
                    });
        };
    }

    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}


