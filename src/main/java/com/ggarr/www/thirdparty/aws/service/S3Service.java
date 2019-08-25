package com.ggarr.www.thirdparty.aws.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.net.URL;

public interface S3Service {
    PutObjectResponse upload(MultipartFile file, String filename) throws IOException;

    URL getUrl(String key);
}
