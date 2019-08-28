package com.ggarr.www.controller;

import com.ggarr.www.entity.PostEntity;
import com.ggarr.www.service.PostService;
import com.ggarr.www.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private PostService postService;

    @Autowired
    private ReactionService reactionService;

    @GetMapping(value = "/")
    public String home(
            @RequestParam(required = false) String query,
            @PageableDefault(sort = "idx", direction = Sort.Direction.DESC) Pageable pageable,
            Model model
    ) {
        Page<PostEntity> postList = postService.findAllPost(pageable);
        model.addAttribute("query", query);
        model.addAttribute("postList", postList);
        return "index";
    }
}
