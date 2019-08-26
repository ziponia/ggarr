package com.ggarr.www.controller.rest;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.FileEntity;
import com.ggarr.www.entity.ReactionEntity;
import com.ggarr.www.service.FileService;
import com.ggarr.www.service.ReactionService;
import com.ggarr.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class UtilController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReactionService reactionService;

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

    @PostMapping(value = "/auth/check-params")
    public ResponseEntity<Object> checkParams(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String password
    ) {
        HashMap<String, Object> hm = new HashMap<>();
        boolean allow = userService.checkAllowUser(email, name, password);
        hm.put("status", allow);
        return ResponseEntity.ok(hm);
    }

    @PostMapping(value = "/reaction")
    public ResponseEntity<Object> updateReaction(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam Integer postIdx,
            @RequestParam ReactionEntity.ReactionType reactionType
    ) {
        HashMap<String, Object> hm = new HashMap<>();
        if (userPrincipal == null) {
            hm.put("message", "Authentication Required.");
            return new ResponseEntity<>(hm, HttpStatus.UNAUTHORIZED);
        }
        ReactionEntity response = reactionService.userReaction(postIdx, reactionType, userPrincipal);
        hm.put("type", response == null ? null : response.getReactionType());
        hm.put("like", reactionService.countReactionByPost(postIdx, ReactionEntity.ReactionType.LIKE));
        hm.put("no", reactionService.countReactionByPost(postIdx, ReactionEntity.ReactionType.NO));
        hm.put("sad", reactionService.countReactionByPost(postIdx, ReactionEntity.ReactionType.SAD));
        hm.put("unbelieve", reactionService.countReactionByPost(postIdx, ReactionEntity.ReactionType.UNBELIEVE));
        return ResponseEntity.ok(hm);
    }
}
