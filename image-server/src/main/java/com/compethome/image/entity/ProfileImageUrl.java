package com.compethome.image.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
@AllArgsConstructor
public class ProfileImageUrl {

    @Id
    private String userId;

    private String imageUrl;
}
