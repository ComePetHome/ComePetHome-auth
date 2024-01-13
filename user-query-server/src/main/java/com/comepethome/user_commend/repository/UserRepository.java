package com.comepethome.user_commend.repository;

import com.comepethome.user_commend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    User findByNameAndPhoneNumber(String name, String phoneNumber);
}
