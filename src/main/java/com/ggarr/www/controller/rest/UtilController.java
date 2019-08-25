package com.ggarr.www.controller.rest;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.FileEntity;
import com.ggarr.www.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class UtilController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/file-upload")
    public ResponseEntity<Object> fileUpload(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            MultipartFile file
    ) throws IOException {
        HashMap<String, Object> hm = new HashMap<>();
        FileEntity fileEntity = fileService.upload(file, userPrincipal);
        hm.put("link", fileEntity.getFilePath());
        return ResponseEntity.ok(hm);
    }
}
