package com.project.compethome.entity;

import com.project.compethome.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column
    private final String userId;

    @Column
    private String password;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @Column
    private String nickName;

    @Column
    private String imageUrl;

    @Column
    private LocalDateTime createAt;

    @Column
    private LocalDateTime updateAt;


    public static User translate(UserDTO userDTO, PasswordEncoder encoder){
        return new User(null,
                            userDTO.getUserId(),
                            encoder.encode(userDTO.getPassword()),
                            userDTO.getAccessToken(),
                            userDTO.getRefreshToken(),
                            userDTO.getNickName(),
                            userDTO.getImageUrl(),
                            userDTO.getCreateAt(),
                            userDTO.getUpdateAt());
    }

}
