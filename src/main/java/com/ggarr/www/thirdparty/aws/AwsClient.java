package com.ggarr.www.thirdparty.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class AwsClient {

    @Value("${aws.accessKeyId}")
    private String AWS_ACCESS_KEY_ID;

    @Value("${aws.secretAccessKey}")
    private String AWS_SECRET_KEY;

    @Bean
    public StaticCredentialsProvider staticCredentialsProvider() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                AWS_ACCESS_KEY_ID, AWS_SECRET_KEY
        );
        return StaticCredentialsProvider.create(credentials);
    }

    @Bean
    public S3Client s3Client() {

        return S3Client.
                builder()
                .credentialsProvider(staticCredentialsProvider())
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
}
