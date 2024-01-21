package com.comepethome.user_command.service;

import com.comepethome.user_command.entity.User;
import com.comepethome.user_command.dto.UserDTO;
import com.comepethome.user_command.exception.user.UserAlreadyExistException;
import com.comepethome.user_command.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public void save(UserDTO userDTO){
        log.info("join service position - {}", System.currentTimeMillis() / 1000);
        findByUser(userDTO.getUserId())
                .ifPresentOrElse(
                        user -> { throw new UserAlreadyExistException(); },
                        () -> userRepository.save(User.translate(userDTO, encoder))
                );
    }

    @Transactional
    private Optional<User> findByUser(String userId){
        return Optional.ofNullable(userRepository.findByUserId(userId));
    }


    @Transactional
    public void update(UserDTO userDTO) {
        User user = userRepository.findByUserId(userDTO.getUserId());

        user.setUpdateAt(userDTO.getUpdateAt());

        Optional.ofNullable(userDTO.getNickName()).ifPresent(user::setNickName);
        Optional.ofNullable(userDTO.getPhoneNumber())
                .filter(phoneNumber -> !phoneNumber.trim().isEmpty())
                .ifPresent(user::setPhoneNumber);
    }

    @Transactional
    public void delete(UserDTO userDTO) {
        findByUser(userDTO.getUserId()).ifPresent(userRepository::delete);
    }
}
