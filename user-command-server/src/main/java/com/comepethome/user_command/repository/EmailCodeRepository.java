package com.comepethome.user_command.repository;

import com.comepethome.user_command.entity.EmailCode;
import com.comepethome.user_command.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {
    EmailCode findByUserId(String userId);
}
