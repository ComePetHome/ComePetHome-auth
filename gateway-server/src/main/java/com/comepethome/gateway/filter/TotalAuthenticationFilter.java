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
public class TotalAuthenticationFilter extends AbstractGatewayFilterFactory<TotalAuthenticationFilter.Config> {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    public TotalAuthenticationFilter(JwtService jwtService){
        super(TotalAuthenticationFilter.Config.class);
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

            if(request.getHeaders().containsKey(Common.ACCESS_TOKEN_SUBJECT)){
                List<String> accessToken = request.getHeaders().get(Common.ACCESS_TOKEN_SUBJECT);
                String userId = jwtService.resolveAccessTokenWithPrefix(accessToken.get(0));
                if(userId.isEmpty()){
                    throw new UnexpectedRefreshTokenException();
                }

                Optional<String> path = Optional.ofNullable(request.getURI().getPath());

                request.mutate().header(Common.USER_ID, userId).build();
            }else{
                request.mutate().header(Common.USER_ID, "");
            }

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

