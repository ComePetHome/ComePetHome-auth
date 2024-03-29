package com.comepethome.gateway.filter;

import com.comepethome.gateway.jwt.JwtService;
import com.comepethome.gateway.jwt.dto.TokenDTO;
import com.comepethome.gateway.jwt.exception.token.UnexpectedRefreshTokenException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
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
            ServerHttpResponse response = exchange.getResponse();

            if (request.getHeaders().containsKey(Common.REFRESH_TOKEN_SUBJECT)) {
                List<String> refreshToken = request.getHeaders().get(Common.REFRESH_TOKEN_SUBJECT);
                assert refreshToken != null;
                TokenDTO tokenDTO = jwtService.reissue(refreshToken.get(0));

                response.getHeaders().add(Common.ACCESS_TOKEN_SUBJECT, tokenDTO.getAccessToken());
                response.getHeaders().add(Common.REFRESH_TOKEN_SUBJECT, tokenDTO.getRefreshToken());
                return response.setComplete();
            }

            return Optional.ofNullable(request.getHeaders().get(Common.ACCESS_TOKEN_SUBJECT))
                    .map(accessToken -> {
                        String userId = jwtService.resolveAccessTokenWithPrefix(accessToken.get(0));
                        if(userId.isEmpty()){
                           throw new UnexpectedRefreshTokenException();
                        }

                        Optional<String> path = Optional.ofNullable(request.getURI().getPath());

                        path.filter( p -> p.equals(Common.USER_WITHDRAW_URI) || p.equals(Common.USER_LOGOUT_URI))
                                .ifPresent(p ->{jwtService.deleteRefreshToken(userId);});

                        request.mutate().header("userId", userId).build();
                        return chain.filter(exchange);
                    })
                    .orElseThrow(UnexpectedRefreshTokenException::new);
        };
    }

    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}


