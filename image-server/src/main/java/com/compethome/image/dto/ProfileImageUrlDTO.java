package com.compethome.image.dto;

import com.compethome.image.entity.ProfileImageUrl;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class ProfileImageUrlDTO {
    private String userId;
    private List<String> imageUrls;
    private List<MultipartFile> multipartFile;

    public static ProfileImageUrlDTO translateOut(ProfileImageUrl profileImageUrl) {
        return new ProfileImageUrlDTO(profileImageUrl.getUserId(), profileImageUrl.getImageUrls(), null);
    }

    public static ProfileImageUrlDTO translateIn(String userId, List<MultipartFile> multipartFiles) {
        return new ProfileImageUrlDTO(userId, null, multipartFiles);
    }

    public static ProfileImageUrlDTO translateOut(String userId, List<String> imageUrls) {
        return new ProfileImageUrlDTO(userId, imageUrls, null);
    }
}
