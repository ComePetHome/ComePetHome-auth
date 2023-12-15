package com.project.compethome.entity;

import com.project.compethome.dto.UsersDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column
    private final String userId;

    @Column
    private final String password;

    @Column
    private final String name;

    @Column
    private final String phoneNumber;

    public static Users translate(UsersDTO usersDTO){
        return new Users(null, usersDTO.getUserId(), usersDTO.getPassword(), usersDTO.getName(), usersDTO.getPhoneNumber());
    }

}
