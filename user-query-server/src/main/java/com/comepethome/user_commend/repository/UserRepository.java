package com.comepethome.user_commend.repository;

import com.comepethome.user_commend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUserId(String userId);
    User findByNameAndPhoneNumber(String name, String phoneNumber);
}
