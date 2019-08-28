package com.ggarr.www.controller;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.dto.ReplySaveRequest;
import com.ggarr.www.dto.ReplyVo;
import com.ggarr.www.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/comment/delete")
    public void removeComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam Integer postIdx,
            @RequestParam Integer commentIdx,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        commentService.deleteComment(commentIdx, userPrincipal);
        response.sendRedirect("/posts/" + postIdx);
    }

    @GetMapping(value = "/v/comment/{commentIdx}/reply")
    @ResponseBody
    public ResponseEntity<Collection<ReplyVo>> findReply(
            @PathVariable Integer commentIdx
    ) {
        Collection<ReplyVo> replyList = commentService.findAllReply(commentIdx);
        return ResponseEntity.ok(replyList);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(value = "/v/comment/{replyIdx}")
    @ResponseBody
    public ResponseEntity<Object> deleteReply(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Integer replyIdx
    ) {
        boolean removeComment = commentService.removeComment(replyIdx, userPrincipal);
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("result", removeComment);
        return new ResponseEntity<>(hm, removeComment ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/v/comment/reply")
    public ResponseEntity<Object> createReply(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ReplySaveRequest replySaveRequest
    ) {
        if (userPrincipal == null) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("result", false);
            return new ResponseEntity<>(hm, HttpStatus.UNAUTHORIZED);
        }
        ReplyVo reply = commentService.saveReply(replySaveRequest, userPrincipal);
        return ResponseEntity.ok(reply);
    }
}
