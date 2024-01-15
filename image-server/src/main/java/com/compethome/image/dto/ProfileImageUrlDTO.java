package com.compethome.image.dto;

import com.compethome.image.entity.ProfileImageUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ProfileImageUrlDTO {
    private String userId;
    private String imageUrl;
    private MultipartFile multipartFile;

    public static ProfileImageUrlDTO translate(ProfileImageUrl profileImageUrl) {
        return new ProfileImageUrlDTO(profileImageUrl.getUserId(), profileImageUrl.getImageUrl(), null);
    }

    public static ProfileImageUrlDTO translate(String userId, MultipartFile multipartFile) {
        return new ProfileImageUrlDTO(userId, null, multipartFile);
    }

    public static ProfileImageUrlDTO translate(String imageUrl) {
        return new ProfileImageUrlDTO(null, imageUrl, null);
    }
}
