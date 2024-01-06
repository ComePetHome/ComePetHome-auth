package com.comepethome.user_commend.entity;

import com.comepethome.user_commend.dto.UserDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document(collection = "users")
public class User {

    private final Long id;
    private final String user_id;
    private String password;
    private String access_token;
    private String refresh_token;
    private String nick_name;
    private String image_url;
    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private final String name;
    private final String phone_number;


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
