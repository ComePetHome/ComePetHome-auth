package com.comepethome.user_query.service;

import com.comepethome.user_query.entity.User;
import com.comepethome.user_query.dto.UserDTO;
import com.comepethome.user_query.exception.user.UserAlreadyExistException;
import com.comepethome.user_query.exception.user.UserNotExistException;
import com.comepethome.user_query.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public void save(UserDTO userDTO){
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
        Optional.ofNullable(userDTO.getName()).ifPresent(user::setName);
        Optional.ofNullable(userDTO.getPhoneNumber())
                .filter(phoneNumber -> !phoneNumber.trim().isEmpty())
                .ifPresent(user::setPhoneNumber);
        Optional.ofNullable(userDTO.getImageUrl()).ifPresent(user::setImageUrl);
    }
}
