package com.compethome.image.repository;

import com.compethome.image.entity.ProfileImageUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileImageUrlRepository extends MongoRepository<ProfileImageUrl, String> {
}
