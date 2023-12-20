package com.project.compethome.service;

import com.project.compethome.dto.UserDTO;
import com.project.compethome.entity.User;
import com.project.compethome.exception.user.UserAlreadyExistException;
import com.project.compethome.exception.user.UserNotExistException;
import com.project.compethome.exception.user.UserPasswordNotMatchException;
import com.project.compethome.repository.UserRepository;
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
