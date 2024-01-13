package com.comepethome.user_commend.service;

import com.comepethome.user_commend.entity.User;
import com.comepethome.user_commend.dto.UserDTO;
import com.comepethome.user_commend.exception.user.UserAlreadyExistException;
import com.comepethome.user_commend.exception.user.UserNotExistException;
import com.comepethome.user_commend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String findUserIdByNameAndPhoneNumber(UserDTO userDTO){
        return Optional.ofNullable(userRepository.findByNameAndPhoneNumber(userDTO.getName(), userDTO.getPhoneNumber()))
                .map(User::getUserId)
                .orElseThrow(UserNotExistException::new);
    }

    @Transactional
    public Optional<User> findByUser(String userId){
        return Optional.ofNullable(userRepository.findByUserId(userId));
    }

    @Transactional
    public UserDTO getProfile(UserDTO userDTO) {
        return findByUser(userDTO.getUserId())
                .map(UserDTO::translate)
                .orElseThrow(UserNotExistException::new);
    }

    @Transactional
    public String getAvailableUserId(UserDTO userDTO) {
        findByUser(userDTO.getUserId())
                .ifPresent(
                    user ->{ throw new UserAlreadyExistException();
                });
        return userDTO.getUserId();
    }
}
