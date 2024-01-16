package com.compethome.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.compethome.image.dto.ProfileImageUrlDTO;
import com.compethome.image.entity.ProfileImageUrl;
import com.compethome.image.exception.image.ImageFileNameWrongException;
import com.compethome.image.exception.image.ImageNotExistException;
import com.compethome.image.exception.image.ImageUploadFailedException;
import com.compethome.image.exception.image.UserAlreadyExistException;
import com.compethome.image.repository.ProfileImageUrlRepository;
import com.ctc.wstx.shaded.msv_core.util.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileImageUrlService {
    @Autowired
    private ProfileImageUrlRepository profileImageUrlRepository;

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    @Value("${cloud.aws.s3.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.s3.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.region}")
    private String region;

    public ProfileImageUrlDTO getProfileUrl(String userId){
        return find(userId).map(ProfileImageUrlDTO::translate).orElseThrow(ImageNotExistException::new);
    }

    private Optional<ProfileImageUrl> find(String userId){
        return profileImageUrlRepository.findById(userId);
    }

    public ProfileImageUrlDTO getImageUrl(String userId) {
        return find(userId)
                .map(user -> ProfileImageUrlDTO.translate(user.getImageUrl()))
                .orElseThrow(ImageNotExistException::new);
    }


    public ProfileImageUrlDTO save(ProfileImageUrlDTO profileImageUrlDTO){
        find(profileImageUrlDTO.getUserId()).ifPresent(user -> {throw new UserAlreadyExistException();});

        String imageUrl = amazonS3SaveImage(profileImageUrlDTO.getMultipartFile());
        profileImageUrlRepository.save(new ProfileImageUrl(profileImageUrlDTO.getUserId(), imageUrl));
        return ProfileImageUrlDTO.translate(imageUrl);
    }


    public void update(ProfileImageUrlDTO profileImageUrlDTO){
        find(profileImageUrlDTO.getUserId()).ifPresentOrElse(
                user -> {
                        amazonS3DeleteImage(user.getImageUrl());
                        user.setImageUrl(amazonS3SaveImage(profileImageUrlDTO.getMultipartFile()));
                        profileImageUrlRepository.save(user);
                    },
                ()-> { throw new ImageNotExistException();}
        );
    }

    public void delete(String userId){
        find(userId).ifPresentOrElse(
                user ->{
                    amazonS3DeleteImage(user.getImageUrl());
                    profileImageUrlRepository.delete(user);
                },
                ()->{ throw new ImageNotExistException();}
        );
    }

    private String createS3SaveFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ImageFileNameWrongException();
        }
    }

    private void amazonS3DeleteImage(String fileName){
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String amazonS3SaveImage(MultipartFile file){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        String s3SaveFileName = createS3SaveFileName(file.getOriginalFilename());

        try(InputStream inputStream = file.getInputStream()){
            amazonS3.putObject(new PutObjectRequest(bucket, s3SaveFileName, inputStream, objectMetadata));
        }catch (IOException e){
            throw new ImageUploadFailedException();
        }

        return amazonS3.getUrl(bucket, s3SaveFileName).toString();
    }
}
