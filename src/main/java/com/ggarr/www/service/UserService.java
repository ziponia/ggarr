package com.ggarr.www.service;

import com.ggarr.www.entity.UserEntity;
import org.springframework.security.core.Authentication;

public interface UserService {

    boolean checkUserEmail(String email);

    UserEntity addUser(UserEntity userEntity);

    Authentication authenticateUser(String email, String password);

    boolean checkAllowUser(String email, String name, String password);
}
