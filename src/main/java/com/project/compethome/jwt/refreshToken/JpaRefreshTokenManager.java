package com.project.compethome.jwt.refreshToken;

import com.project.compethome.entity.User;
import com.project.compethome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class JpaRefreshTokenManager implements RefreshTokenManager {

    private final UserRepository userRepository;

    @Override
    public void update(String userId, String token) {
        User user = userRepository.findByUserId(userId);
        user.setRefreshToken(token);
    }

    @Override
    public Optional<String> find(String userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUserId(userId));
        return user.map(User::getRefreshToken);
    }

    @Override
    public void delete(String userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUserId(userId));
        if (user.isEmpty()) {
            return;
        }

        user.get().setRefreshToken("");
    }

}