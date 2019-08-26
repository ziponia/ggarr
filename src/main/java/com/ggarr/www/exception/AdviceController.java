package com.ggarr.www.exception;

import com.ggarr.www.exception.handler.UserRegisterException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(UserRegisterException.class)
    public ResponseEntity<Object> userRegisterException(Exception e) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(hm);
    }
}
