package com.comepethome.user_commend.service;

import com.comepethome.user_commend.entity.User;
import com.comepethome.user_commend.dto.UserDTO;
import com.comepethome.user_commend.exception.user.UserAlreadyExistException;
import com.comepethome.user_commend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

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
