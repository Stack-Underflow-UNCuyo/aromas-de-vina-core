package com.aromasdevina.core.config;

import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class StorageConfiguration {

    @Bean
    public S3Client s3Client(ApplicationProperties properties) {
        ApplicationProperties.Storage storage = properties.getStorage();
        return S3Client.builder()
            .credentialsProvider(
                StaticCredentialsProvider.create(AwsBasicCredentials.create(storage.getAccessKey(), storage.getSecretKey()))
            )
            .region(Region.of(storage.getRegion()))
            .endpointOverride(URI.create(storage.getEndpoint()))
            .forcePathStyle(true)
            .build();
    }

    @Bean
    public S3Presigner s3Presigner(ApplicationProperties properties) {
        ApplicationProperties.Storage storage = properties.getStorage();
        return S3Presigner.builder()
            .credentialsProvider(
                StaticCredentialsProvider.create(AwsBasicCredentials.create(storage.getAccessKey(), storage.getSecretKey()))
            )
            .region(Region.of(storage.getRegion()))
            .endpointOverride(URI.create(storage.getEndpoint()))
            .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
            .build();
    }
}
