package com.comepethome.user_query.repository;

import com.comepethome.user_query.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUserId(String userId);
    User findByNameAndPhoneNumber(String name, String phoneNumber);
}
