package com.comepethome.gateway.filter;

import com.comepethome.gateway.jwt.JwtService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
        return (exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(()->{
                ServerHttpResponse response = exchange.getResponse();
                Optional.ofNullable(response.getHeaders().getFirst(AUTH_ID)).ifPresent(userId -> {

                    String accessToken = jwtService.createAccessTokenWithPrefix(userId);
                    String refreshToken = jwtService.createRefreshTokenWithPrefix(userId);

                    response.getHeaders().add(ACCESS_TOKEN_SUBJECT, accessToken);
                    response.getHeaders().add(REFRESH_TOKEN_SUBJECT, refreshToken);
            });
        }));
    }

    public String getTimeDate(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter);
    }

    @Data
    public static class Config{
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}


