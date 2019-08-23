package com.ggarr.www.controller;

import com.ggarr.www.entity.UserEntity;
import com.ggarr.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/auth/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @GetMapping(value = "/auth/register")
    public String registerPage(Model model) {
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping(value = "/auth/register")
    public void register(UserEntity userEntity, HttpServletResponse response) throws IOException {
        String n_password = userEntity.getPassword();
        UserEntity entity = userService.addUser(userEntity);
        userService.authenticateUser(entity.getEmail(), n_password);
        response.sendRedirect("/");
    }
}
