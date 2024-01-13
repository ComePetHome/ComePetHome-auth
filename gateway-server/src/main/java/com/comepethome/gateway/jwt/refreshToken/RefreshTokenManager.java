package com.comepethome.gateway.jwt.refreshToken;

import java.util.Optional;

public interface RefreshTokenManager {

    void update(String username, String token);

    Optional<String> find(String username);

    void delete(String userId);

}