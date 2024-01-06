package com.comepethome.user_commend.entity;

import com.comepethome.user_commend.dto.UserDTO;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document(collection = "users")
public class User {

    @Id
    @Field("_id")
    private ObjectId objectId;

    private Long id;

    @Field("user_id")
    private String userId;

    private String password;

    @Field("access_token")
    private String accessToken;

    @Field("refresh_token")
    private String refreshToken;

    @Field("nick_name")
    private String nickName;

    @Field("image_url")
    private String imageUrl;

    @Field("create_at")
    private LocalDateTime createAt;

    @Field("update_at")
    private LocalDateTime updateAt;

    private String name;

    @Field("phone_number")
    private String phoneNumber;

    public static User translate(UserDTO userDTO, PasswordEncoder encoder){
        return new User(null,
                            0L,
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
