package com.project.compethome.repository;

import com.project.compethome.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository <Users, Long> {
    Users findByUserId(String userId);
}
