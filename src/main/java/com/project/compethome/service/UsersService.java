package com.project.compethome.service;

import com.project.compethome.dto.UsersDTO;
import com.project.compethome.entity.Users;
import com.project.compethome.exception.users.UserAlreadyExistException;
import com.project.compethome.exception.users.UserNotExistException;
import com.project.compethome.exception.users.UserPasswordNotMatchException;
import com.project.compethome.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    public void save(UsersDTO usersDTO){
        findByUser(usersDTO.getUserId())
                .ifPresentOrElse(
                        user -> { throw new UserAlreadyExistException(); },
                        () -> usersRepository.save(Users.translate(usersDTO))
                );
    }

    @Transactional
    public void login(UsersDTO usersDTO){
        Optional<Users> user = findByUser(usersDTO.getUserId());

        user.orElseThrow(UserNotExistException::new);
        user.filter(u -> u.getPassword().equals(usersDTO.getPassword()))
                .orElseThrow(UserPasswordNotMatchException::new);
    }


    @Transactional
    public Optional<Users> findByUser(String userId){
        return Optional.ofNullable(usersRepository.findByUserId(userId));
    }
}
