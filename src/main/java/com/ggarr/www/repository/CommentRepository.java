package com.ggarr.www.repository;

import com.ggarr.www.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    Page<CommentEntity> findAllByPostIdxAndCommentReplyIsNull(Integer postIdx, Pageable pageable);

//    @Query("select v from CommentEntity v where v.commentReply.idx = :commentIdx")
    <T> Collection<T> findAllByCommentReplyIdxOrderByIdxDesc(Integer commentIdx, Class<T> type);

    <T> T findByIdx(Integer commentIdx, Class<T> type);
}
