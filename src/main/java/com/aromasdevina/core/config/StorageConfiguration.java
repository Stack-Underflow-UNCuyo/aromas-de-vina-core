package com.aromasdevina.core.config;

import java.net.URI;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class StorageConfiguration {

    private static final String POLICY = """
        {
          "Version": "2012-10-17",
          "Statement": [{
            "Effect": "Allow",
            "Principal": "*",
            "Action": ["s3:GetObject"],
            "Resource": ["arn:aws:s3:::%s/public/*"]
          }]
        }
        """;

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

    @Bean
    public ApplicationRunner createBucket(S3Client s3Client, ApplicationProperties properties) {
        return args -> {
            String bucket = properties.getStorage().getBucket();

            try {
                s3Client.headBucket(request -> request.bucket(bucket));
            } catch (NoSuchBucketException e) {
                s3Client.createBucket(createBucketRequest -> createBucketRequest.bucket(bucket));
            }

            s3Client.putBucketPolicy(request -> request.bucket(bucket).policy(String.format(POLICY, bucket)));
        };
    }
}
