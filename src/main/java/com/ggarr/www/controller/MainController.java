package com.ggarr.www.controller;

import com.ggarr.www.entity.PostEntity;
import com.ggarr.www.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "/")
    public String home(
            @RequestParam(required = false) String query,
            Pageable pageable,
            Model model
    ) {
        Page<PostEntity> postList = postService.findAllPost(pageable);
        model.addAttribute("query", query);
        model.addAttribute("postList", postList);
        return "index";
    }
}
