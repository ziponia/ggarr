package com.ggarr.www.controller;

import com.ggarr.www.entity.UserEntity;
import com.ggarr.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void globalAttributeHandler(Model model, Authentication authentication) {
        if (authentication != null) {
            UserEntity userEntity = userRepository.findByEmail(authentication.getName());
            model.addAttribute("user_nick", userEntity.getName());
        }
    }
}
