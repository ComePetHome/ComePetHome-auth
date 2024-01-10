package com.comepethome.user_commend.service;

import com.comepethome.user_commend.entity.User;
import com.comepethome.user_commend.dto.UserDTO;
import com.comepethome.user_commend.exception.user.UserBadCredentialException;
import com.comepethome.user_commend.exception.user.UserNotExistException;
import com.comepethome.user_commend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String findUserIdByNameAndPhoneNumber(UserDTO userDTO){
        return Optional.ofNullable(userRepository.findByNameAndPhoneNumber(userDTO.getName(), userDTO.getPhoneNumber()))
                .map(User::getUserId)
                .orElseThrow(UserBadCredentialException::new);
    }

    public Optional<User> findByUser(String userId){
        return Optional.ofNullable(userRepository.findByUserId(userId));
    }

    public UserDTO getProfile(UserDTO userDTO) {
        return findByUser(userDTO.getUserId())
                .map(UserDTO::translate)
                .orElseThrow(UserNotExistException::new);
    }
}
