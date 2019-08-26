package com.ggarr.www.repository;

import com.ggarr.www.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

    @Modifying
    @Query(value = "update PostEntity v set v.viewCount = :viewCount where v.idx = :postIdx")
    void updatePostViewerCount(@Param("postIdx") Integer postIdx, @Param("viewCount") Integer viewCount);
}
