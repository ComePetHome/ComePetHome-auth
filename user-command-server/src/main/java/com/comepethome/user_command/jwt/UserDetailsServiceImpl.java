package com.comepethome.user_command.jwt;

import com.comepethome.user_command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        com.comepethome.user_command.entity.User user = Optional.ofNullable(userRepository.findByUserId(userId))
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다"));

        return User.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .build();
    }
}