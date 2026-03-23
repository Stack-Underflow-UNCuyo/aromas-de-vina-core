package com.aromasdevina.core.service.impl;

import com.aromasdevina.core.config.ApplicationProperties;
import com.aromasdevina.core.service.StorageService;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@Service
public class StorageServiceImpl implements StorageService {

    private final S3Presigner s3Presigner;
    private final ApplicationProperties.Storage storage;

    public StorageServiceImpl(S3Presigner s3Presigner, ApplicationProperties properties) {
        this.s3Presigner = s3Presigner;
        this.storage = properties.getStorage();
    }

    @Override
    public URI presignPut(String key, String contentType, Duration expiry) {
        PutObjectRequest putRequest = PutObjectRequest.builder().bucket(storage.getBucket()).key(key).contentType(contentType).build();

        PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(r -> r.signatureDuration(expiry).putObjectRequest(putRequest));

        try {
            return presigned.url().toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to generate presigned URL", e);
        }
    }

    @Override
    public URI presignGet(String key, Duration expiry) {
        GetObjectRequest getRequest = GetObjectRequest.builder().bucket(storage.getBucket()).key(key).build();

        PresignedGetObjectRequest presigned = s3Presigner.presignGetObject(r -> r.signatureDuration(expiry).getObjectRequest(getRequest));

        try {
            return presigned.url().toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to generate presigned URL", e);
        }
    }
}
