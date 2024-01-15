package com.compethome.image.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class S3Properties {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
}