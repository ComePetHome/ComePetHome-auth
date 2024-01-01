package com.comepethome.user.service;

import com.comepethome.user.entity.User;
import com.comepethome.user.dto.UserDTO;
import com.comepethome.user.exception.user.UserAlreadyExistException;
import com.comepethome.user.repository.UserRepository;
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

//    @Transactional
//    public void login(UserDTO userDTO){
//        Optional<User> user = findByUser(userDTO.getUserId());
//
//        user.orElseThrow(UserNotExistException::new);
//        user.filter(u -> u.getPassword().equals(userDTO.getPassword()))
//                .orElseThrow(UserPasswordNotMatchException::new);
//    }


    @Transactional
    public Optional<User> findByUser(String userId){
        return Optional.ofNullable(userRepository.findByUserId(userId));
    }
}
