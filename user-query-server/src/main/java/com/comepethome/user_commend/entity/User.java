package com.comepethome.user_commend.entity;

import com.comepethome.user_commend.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document(collection = "users")
public class User {

    @Id
    private final Long id;
    private final String userId;
    private String password;
    private String accessToken;
    private String refreshToken;
    private String nickName;
    private String imageUrl;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private final String name;
    private final String phoneNumber;


    public static User translate(UserDTO userDTO, PasswordEncoder encoder){
        return new User(null,
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
