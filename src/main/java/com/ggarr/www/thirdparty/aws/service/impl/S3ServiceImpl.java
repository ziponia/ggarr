package com.ggarr.www.thirdparty.aws.service.impl;

import com.ggarr.www.thirdparty.aws.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.net.URL;

@Service
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucket}")
    private String BUCKET;

    @Autowired
    private S3Client s3Client;

    @Override
    public PutObjectResponse upload(MultipartFile file, String filenamne) throws IOException {

        PutObjectRequest request = PutObjectRequest.builder()
                .acl(ObjectCannedACL.PUBLIC_READ)
                .bucket(BUCKET)
                .key(filenamne)
                .build();

        RequestBody body = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
        return s3Client.putObject(request, body);
    }

    @Override
    public URL getUrl(String key) {
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(BUCKET)
                .region(Region.AP_NORTHEAST_2)
                .key(key)
                .build();
        return S3Utilities.builder().region(Region.AP_NORTHEAST_2).build().getUrl(request);
    }
}
