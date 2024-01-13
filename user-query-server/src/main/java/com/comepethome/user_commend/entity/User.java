package com.comepethome.user_commend.entity;

import com.comepethome.user_commend.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

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
    private String userId;

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

    @Column
    private String name;

    @Column
    private String phoneNumber;

    public static User translate(UserDTO userDTO, PasswordEncoder encoder){
        return new User( 0L,
                            userDTO.getUserId(),
                            encoder.encode(userDTO.getPassword()),
                            userDTO.getAccessToken(),
                            userDTO.getRefreshToken(),
                            userDTO.getNickName(),
                            userDTO.getImageUrl(),
                            userDTO.getCreateAt(),
                            userDTO.getUpdateAt(),
                            userDTO.getName(),
                            userDTO.getPhoneNumber()
                );
    }

}
