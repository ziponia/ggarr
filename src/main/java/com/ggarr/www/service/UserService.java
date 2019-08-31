package com.ggarr.www.service;

import com.ggarr.www.dto.PostListProjection;
import com.ggarr.www.entity.PostEntity;
import com.ggarr.www.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserService {

    boolean checkUserEmail(String email);

    UserEntity addUser(UserEntity userEntity);

    Authentication authenticateUser(String email, String password);

    boolean checkAllowUser(String email, String name, String password);

    Page<PostListProjection> findPostByUsername(String username, Pageable pageable);
}
