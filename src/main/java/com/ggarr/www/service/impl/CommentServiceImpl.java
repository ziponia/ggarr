package com.ggarr.www.service.impl;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.dto.ReplySaveRequest;
import com.ggarr.www.dto.ReplyVo;
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

import java.util.Collection;

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
        return commentRepository.findAllByPostIdxAndCommentReplyIsNull(postIdx, pageable);
    }

    @Override
    @Transactional
    public void deleteComment(Integer commentIdx, UserPrincipal userPrincipal) {
        CommentEntity commentEntity = commentRepository.getOne(commentIdx);
        if (commentEntity.getCreateUser().getIdx().equals(userPrincipal.getIdx())) {
            commentRepository.deleteById(commentIdx);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ReplyVo> findAllReply(Integer commentIdx) {
        return commentRepository.findAllByCommentReplyIdxOrderByIdxDesc(commentIdx, ReplyVo.class);
    }

    @Override
    @Transactional
    public boolean removeComment(Integer replyIdx, UserPrincipal userPrincipal) {
        CommentEntity commentEntity = commentRepository.getOne(replyIdx);
        if (!commentEntity.getCreateUser().getIdx().equals(userPrincipal.getIdx())) {
            return false;
        }
        commentRepository.deleteById(replyIdx);
        return true;
    }

    @Override
    @Transactional
    public ReplyVo saveReply(ReplySaveRequest replySaveRequest, UserPrincipal userPrincipal) {
        UserEntity userEntity = userRepository.getOne(userPrincipal.getIdx());
        PostEntity postEntity = postRepository.getOne(replySaveRequest.getTargetPostIdx());
        CommentEntity commentEntity = commentRepository.getOne(replySaveRequest.getTargetCommentIdx());
        CommentEntity newComment = new CommentEntity();
        newComment.setCreateUser(userEntity);
        newComment.setPost(postEntity);
        newComment.setContent(replySaveRequest.getContent());
        newComment.setCommentReply(commentEntity);
        commentRepository.save(newComment);

        return ReplyVo.builder()
                .idx(newComment.getIdx())
                .content(newComment.getContent())
                .createUser(newComment.getCreateUser())
                .createTime(newComment.getCreateTime())
                .updateTime(newComment.getUpdateTime())
                .build();
    }
}
