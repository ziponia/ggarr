package com.ggarr.www.controller;

import com.ggarr.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class RestUserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/v/check-user-email")
    public ResponseEntity<HashMap<String, Object>> checkUserEmail(
            @RequestParam String email
    ) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("status", userService.checkUserEmail(email));
        return ResponseEntity.ok(hm);
    }
}
