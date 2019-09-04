package com.ggarr.www.controller;

import com.ggarr.www.entity.PostEntity;
import com.ggarr.www.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "/")
    public String home(
            @RequestParam(required = false) String query,
            @PageableDefault(sort = "idx", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request,
            Model model
    ) {
        if (query != null && query.length() < 1) {
            return "redirect:/";
        } else if (query != null) {
            model.addAttribute("siteTitle", "검색결과 - " + query);
            model.addAttribute("siteKeyword", query);
        }

        Page<PostEntity> postList = postService.findAllPost(query, pageable);

        model.addAttribute("query", query);
        model.addAttribute("postList", postList);
        return "index";
    }
}
