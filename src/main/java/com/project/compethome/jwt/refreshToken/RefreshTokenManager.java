package com.project.compethome.jwt.refreshToken;

import java.util.Optional;

public interface RefreshTokenManager {

    void update(String username, String token);

    Optional<String> find(String username);

    void delete(String username);

}