package com.comepethome.gateway.jwt.refreshToken;

import com.comepethome.gateway.entity.RefreshToken;
import com.comepethome.gateway.repository.RefreshTokenRepository;
import com.comepethome.gateway.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JpaRefreshTokenManager implements RefreshTokenManager {

    @Autowired
    private final RefreshTokenService service;

    @Override
    public void update(String userId, String token) {
        service.update(userId,token);
    }

    @Override
    public Optional<String> find(String userId) {
        Optional<RefreshToken> refreshToken = service.find(userId);
        return refreshToken.map(RefreshToken::getRefreshToken);
    }

    @Override
    public void delete(String userId) {
        service.delete(userId);
    }

}