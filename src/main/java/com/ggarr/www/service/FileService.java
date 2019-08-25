package com.ggarr.www.service;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    FileEntity upload(MultipartFile file, UserPrincipal uploader) throws IOException;
}
