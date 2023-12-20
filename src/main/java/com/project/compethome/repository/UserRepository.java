package com.project.compethome.repository;

import com.project.compethome.entity.User;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUserId(String userId);
}
