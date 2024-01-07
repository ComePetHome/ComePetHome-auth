package com.comepethome.gateway.service;

import com.comepethome.gateway.entity.RefreshToken;
import com.comepethome.gateway.jwt.exception.token.TokenUserNotExistException;
import com.comepethome.gateway.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Autowired
    private final RefreshTokenRepository repository;

    @Transactional
    public Optional<RefreshToken> find(String userId){
        Optional<RefreshToken> refreshToken = Optional.ofNullable(repository.findByUserId(userId));
        if(refreshToken.isEmpty()){
            throw new TokenUserNotExistException();
        }
        return refreshToken;
    }

    @Transactional
    public void update(String userId, String token){
        Optional<RefreshToken> refreshToken = Optional.ofNullable(repository.findByUserId(userId));
        refreshToken.ifPresentOrElse(
                item -> item.setRefreshToken(token),
                ()-> repository.save(new RefreshToken(null,userId, token))
        );
    }

    @Transactional
    public void delete(String userId){
        Optional<RefreshToken> refreshToken = find(userId);
        refreshToken.ifPresent(item -> item.setRefreshToken(""));
    }
}
