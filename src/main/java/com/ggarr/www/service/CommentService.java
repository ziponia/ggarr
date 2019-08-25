package com.ggarr.www.service;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentEntity save(CommentEntity comment, Integer postIdx, Integer userIdx);

    Page<CommentEntity> findCommentListByPost(Integer postIdx, Pageable pageable);

    void deleteComment(Integer commentIdx, UserPrincipal userPrincipal);
}
