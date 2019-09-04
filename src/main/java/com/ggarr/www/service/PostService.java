package com.ggarr.www.service;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostEntity save(PostEntity postEntity, UserPrincipal user);

    PostEntity findPost(Integer idx);

    Page<PostEntity> findAllPost(String query, Pageable pageable);

    PostEntity archivePost(PostEntity entity, UserPrincipal userPrincipal);

    void saveViewer(Integer postIdx, UserPrincipal userPrincipal);
}
