package com.ggarr.www.controller;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.PostEntity;
import com.ggarr.www.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @PreAuthorize("hasAnyAuthority('BASIC')")
    @GetMapping(value = "/create")
    public String createPage(Model model) {
        PostEntity post = new PostEntity();
        model.addAttribute("post", post);
        return "post-create";
    }

    @PreAuthorize("hasAnyAuthority('BASIC')")
    @PostMapping(value = "/create")
    public void savePost(HttpServletResponse response, PostEntity postEntity, @AuthenticationPrincipal UserPrincipal user) throws IOException {
        PostEntity entity = postService.save(postEntity, user);
        response.sendRedirect("/posts/" + entity.getIdx());
    }

    @GetMapping(value = "/posts/{idx}")
    public String postDetail(
            @PathVariable Integer idx,
            Model model
    ) throws IOException {
        PostEntity entity = postService.findPost(idx);
        if (entity == null) {
            return "redirect:/";
        }
        model.addAttribute("post", entity);
        return "post-detail";
    }
}
