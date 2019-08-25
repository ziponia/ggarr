package com.ggarr.www.service.impl;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.CommentEntity;
import com.ggarr.www.entity.PostEntity;
import com.ggarr.www.entity.UserEntity;
import com.ggarr.www.repository.CommentRepository;
import com.ggarr.www.repository.PostRepository;
import com.ggarr.www.repository.UserRepository;
import com.ggarr.www.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public CommentEntity save(CommentEntity comment, Integer postIdx, Integer userIdx) {
        PostEntity postEntity = postRepository.getOne(postIdx);
        UserEntity userEntity = userRepository.getOne(userIdx);
        comment.setCreateUser(userEntity);
        comment.setPost(postEntity);
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public Page<CommentEntity> findCommentListByPost(Integer postIdx, Pageable pageable) {
        return commentRepository.findAllByPostIdx(postIdx, pageable);
    }

    @Override
    @Transactional
    public void deleteComment(Integer commentIdx, UserPrincipal userPrincipal) {
        CommentEntity commentEntity = commentRepository.getOne(commentIdx);
        if (commentEntity.getCreateUser().getIdx().equals(userPrincipal.getIdx())) {
            commentRepository.deleteById(commentIdx);
        }
    }
}
