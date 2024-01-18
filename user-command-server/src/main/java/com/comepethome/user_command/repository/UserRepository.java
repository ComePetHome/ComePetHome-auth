package com.comepethome.user_command.repository;

import com.comepethome.user_command.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUserId(String userId);

}
