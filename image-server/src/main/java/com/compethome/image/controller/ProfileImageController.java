package com.compethome.image.controller;

import com.compethome.image.controller.response.UserStatusResponse;
import com.compethome.image.dto.ProfileImageUrlDTO;
import com.compethome.image.exception.ApiExceptionHandler;
import com.compethome.image.service.ProfileImageUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.compethome.image.exception.SuccessResponseMessage;

import java.util.List;

@RestController
@RequestMapping("/image/my-profile")
public class ProfileImageController {
    @Autowired
    private ProfileImageUrlService profileImageUrlService;

    @PostMapping
    public ResponseEntity<ProfileImageUrlDTO> upload(@RequestParam("files") List<MultipartFile> multipartFiles, @RequestHeader("userId") String userId){
        ProfileImageUrlDTO profileImageUrlDTO = profileImageUrlService.save(ProfileImageUrlDTO.translateIn(userId, multipartFiles));
        return ResponseEntity.ok(profileImageUrlDTO);
    }

    @PutMapping
    public ResponseEntity<ProfileImageUrlDTO> update(@RequestParam("files") List<MultipartFile> multipartFiles, @RequestHeader("userId") String userId){
        ProfileImageUrlDTO profileImageUrlDTO = profileImageUrlService.update(ProfileImageUrlDTO.translateIn(userId, multipartFiles));
        return ResponseEntity.ok(profileImageUrlDTO);
    }

    @DeleteMapping
    public ResponseEntity<UserStatusResponse> delete(@RequestHeader("userId") String userId){
        profileImageUrlService.delete(userId);
        return ResponseEntity.ok(new UserStatusResponse(
                SuccessResponseMessage.IMAGE_DELETE_SUCCESS.getMessage(),
                HttpStatus.OK,
                ApiExceptionHandler.getNowDateTime(),
                SuccessResponseMessage.IMAGE_DELETE_SUCCESS.getCode())
        );
    }

    @GetMapping
    public ResponseEntity<List<String>> getImageUrls(@RequestHeader("userId") String userId){
        ProfileImageUrlDTO profileImageUrlDTO = profileImageUrlService.getImageUrl(userId);
        return ResponseEntity.ok(profileImageUrlDTO.getImageUrls());
    }
}
