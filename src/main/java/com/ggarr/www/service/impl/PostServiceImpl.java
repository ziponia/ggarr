package com.ggarr.www.service.impl;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.PostEntity;
import com.ggarr.www.entity.UserEntity;
import com.ggarr.www.repository.PostRepository;
import com.ggarr.www.repository.UserRepository;
import com.ggarr.www.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public PostEntity save(PostEntity postEntity, UserPrincipal user) {
        UserEntity userEntity = userRepository.findById(user.getIdx()).orElse(null);
        postEntity.setCreateUser(userEntity);
        return postRepository.save(postEntity);
    }

    @Override
    public PostEntity findPost(Integer idx) {
        PostEntity postEntity = postRepository.findById(idx).orElse(null);
        return postEntity;
    }

    @Override
    public Page<PostEntity> findAllPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public PostEntity archivePost(PostEntity entity, UserPrincipal userPrincipal) {
        PostEntity dbEntity = postRepository.getOne(entity.getIdx());
        dbEntity.setPublish(entity.isPublish());
        if (!dbEntity.getCreateUser().getIdx().equals(userPrincipal.getIdx())) {
            return null;
        }
        postRepository.save(dbEntity);
        return entity;
    }
}
