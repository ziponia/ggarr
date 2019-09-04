package com.ggarr.www.controller;

import com.ggarr.www.entity.UserEntity;
import com.ggarr.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalController {

    private static final String DEFAULT_SITE_NAME = "꺄르륵 게시판";
    private static final String DEFAULT_SITE_TITLE = "모두가 웃는 소리 꺄르륵꺄르륵";
    private static final String DEFAULT_SITE_DESCRIPTION = "많은 사람들이 웃으며 즐거운 이야기를 했으면 좋겠습니다 꺄르륵";
    private static final String DEFAULT_SITE_KEYWORD = "블로그,게시판,커뮤니티,오픈소스,웃음,문화,키워드";
    private static final String DEFAULT_SITE_AUTHOR = "지포니아";
    private static final String DEFAULT_SITE_TYPE = "게시판";
    private static final String DEFAULT_SITE_IMAGE = "";

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void globalAttributeHandler(
            Model model,
            Authentication authentication,
            HttpServletRequest request
    ) {
        if (authentication != null) {
            UserEntity userEntity = userRepository.findByEmail(authentication.getName());
            model.addAttribute("user_nick", userEntity.getName());
        }

        model.addAttribute("siteName", request.getParameter("siteName") == null ? DEFAULT_SITE_NAME : request.getParameter("siteName"));
        model.addAttribute("siteTitle", request.getParameter("siteTitle") == null ? DEFAULT_SITE_TITLE : request.getParameter("siteTitle"));
        model.addAttribute("siteDesc", request.getParameter("siteDesc") == null ? DEFAULT_SITE_DESCRIPTION : request.getParameter("siteDesc"));
        model.addAttribute("siteKeyword", request.getParameter("siteKeyword") == null ? DEFAULT_SITE_KEYWORD : request.getParameter("siteKeyword"));
        model.addAttribute("siteAuthor", request.getParameter("siteAuthor") == null ? DEFAULT_SITE_AUTHOR : request.getParameter("siteAuthor"));
        model.addAttribute("siteType", request.getParameter("siteType") == null ? DEFAULT_SITE_TYPE : request.getParameter("siteType"));
        model.addAttribute("siteUrl", request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : null));
        model.addAttribute("siteImage", request.getParameter("siteImage") == null ? DEFAULT_SITE_IMAGE : request.getParameter("siteType"));
    }
}
