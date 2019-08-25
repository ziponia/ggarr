package com.ggarr.www.service.impl;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.FileEntity;
import com.ggarr.www.entity.UserEntity;
import com.ggarr.www.repository.FileRepository;
import com.ggarr.www.repository.UserRepository;
import com.ggarr.www.service.FileService;
import com.ggarr.www.thirdparty.aws.service.S3Service;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    @Transactional
    public FileEntity upload(MultipartFile file, UserPrincipal uploader) throws IOException {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        FileEntity.FileType fileType = FileEntity.FileType.FILE;
        UserEntity uploadUser = userRepository.findById(uploader.getIdx()).orElse(null);

        String key = new Date().getTime() + "-" + UUID.randomUUID().toString() + "." + extension;
        s3Service.upload(file, key);
        URL url = s3Service.getUrl(key);

        String contentType = file.getContentType();

        assert contentType != null;
        if (contentType.startsWith("image")) {
            fileType = FileEntity.FileType.IMAGE;
        }
        FileEntity fileEntity = FileEntity.builder()
                .fileType(fileType)
                .fileName(key)
                .filePath(url.toString())
                .originalName(file.getOriginalFilename())
                .uploader(uploadUser)
                .fileSize(file.getSize())
                .build();

        fileRepository.save(fileEntity);
        return fileEntity;
    }
}
