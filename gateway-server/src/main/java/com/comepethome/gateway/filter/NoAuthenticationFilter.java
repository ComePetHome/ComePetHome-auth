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
                Optional.ofNullable(response.getHeaders().getFirst(Common.AUTH_ID)).ifPresent(userId -> {

                    String accessToken = jwtService.createAccessTokenWithPrefix(userId);
                    String refreshToken = jwtService.createRefreshTokenWithPrefix(userId);

                    response.getHeaders().add(Common.ACCESS_TOKEN_SUBJECT, accessToken);
                    response.getHeaders().add(Common.REFRESH_TOKEN_SUBJECT, refreshToken);
                    response.getHeaders().remove(Common.AUTH_ID);
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


