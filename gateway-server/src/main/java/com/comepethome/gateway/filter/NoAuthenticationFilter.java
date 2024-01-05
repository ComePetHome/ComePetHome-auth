package com.comepethome.gateway.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.comepethome.gateway.jwt.JwtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import java.util.Optional;

@Slf4j
@Component
public class NoAuthenticationFilter extends AbstractGatewayFilterFactory<NoAuthenticationFilter.Config> {
    private final static String AUTH_ID = "Auth-Id";
    private static final String ACCESS_TOKEN_SUBJECT = "access-token";
    private static final String REFRESH_TOKEN_SUBJECT = "refresh-token";
    @Autowired
    private final JwtService jwtService;
    @Autowired
    public NoAuthenticationFilter(JwtService jwtService){
        super(NoAuthenticationFilter.Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) ->{
                ServerHttpResponse response = exchange.getResponse();
                ServerHttpRequest request = exchange.getRequest();

                //토큰에서 userId 꺼내서 헤더에 넣는다
                Optional.ofNullable(request.getHeaders().getFirst("Authorization")).ifPresent(token-> {
                    try {
                        String userId = jwtService.getUserIdfromToken(token);
                        request.mutate().header("userId", userId).build();
                    } catch (JWTVerificationException e) {
                        log.error("JWT Verification failed: {}", e.getMessage());
                    }
                });

                Optional.ofNullable(response.getHeaders().getFirst(AUTH_ID)).ifPresent(userId -> {

                    String accessToken = jwtService.createAccessTokenWithPrefix(userId);
                    String refreshToken = jwtService.createRefreshTokenWithPrefix(userId);

                    response.getHeaders().add(ACCESS_TOKEN_SUBJECT, accessToken);
                    response.getHeaders().add(REFRESH_TOKEN_SUBJECT, refreshToken);
            });
         return chain.filter(exchange);
        };
    }

    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}


