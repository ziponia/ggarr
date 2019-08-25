package com.ggarr.www.repository;

import com.ggarr.www.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    Page<CommentEntity> findAllByPostIdx(Integer postIdx, Pageable pageable);
}
