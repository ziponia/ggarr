package com.ggarr.www.controller;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
}
