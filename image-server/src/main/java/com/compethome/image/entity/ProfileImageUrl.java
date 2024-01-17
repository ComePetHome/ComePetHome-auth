package com.compethome.image.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "image_profile")
@AllArgsConstructor
public class ProfileImageUrl {

    @Id
    private String userId;

    private List<String> imageUrls;

    public static ProfileImageUrl translate(String userId, List<String> imageUrls){
        return new ProfileImageUrl(userId, imageUrls);
    }

}
