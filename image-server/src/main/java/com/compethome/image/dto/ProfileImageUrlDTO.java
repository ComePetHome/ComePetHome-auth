package com.compethome.image.dto;

import com.compethome.image.entity.ProfileImageUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@AllArgsConstructor
public class ProfileImageUrlDTO {
    private final String userId;
    private final String imageUrl;
    private final MultipartFile multipartFile;

    public static ProfileImageUrlDTO traslate(ProfileImageUrl profileImageUrl) {
        return new ProfileImageUrlDTO(profileImageUrl.getUserId(), profileImageUrl.getImageUrl(), null);
    }

    public static ProfileImageUrlDTO traslate(String userId, MultipartFile multipartFile) {
        return new ProfileImageUrlDTO(userId, null, multipartFile);
    }

    public static ProfileImageUrlDTO traslate(String imageUrl) {
        return new ProfileImageUrlDTO(null, imageUrl, null);
    }
}
