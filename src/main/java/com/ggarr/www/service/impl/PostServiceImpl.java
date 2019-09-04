package com.ggarr.www.service.impl;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.PostEntity;
import com.ggarr.www.entity.PostViewEntity;
import com.ggarr.www.entity.UserEntity;
import com.ggarr.www.repository.PostRepository;
import com.ggarr.www.repository.UserRepository;
import com.ggarr.www.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class PostServiceImpl implements PostService {

    @PersistenceContext
    private EntityManager em;

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
    public Page<PostEntity> findAllPost(String query, Pageable pageable) {
        if (query == null) {
            query = "";
        }
        return postRepository.findAllPosts(query, pageable);
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

    @Override
    @Transactional
    public void saveViewer(Integer postIdx, UserPrincipal userPrincipal) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String sessionId = request.getSession().getId();
        PostViewEntity postViewEntity;
        TypedQuery<PostViewEntity> query;

        if (userPrincipal == null) {
            query = em.createQuery("select v from PostViewEntity v where v.sessionId = :sessionId and v.postIdx = :postIdx", PostViewEntity.class)
                    .setParameter("sessionId", sessionId)
                    .setParameter("postIdx", postIdx);
        } else {
            query = em.createQuery("select v from PostViewEntity v where v.postIdx = :postIdx and v.userIdx = :userIdx", PostViewEntity.class)
                    .setParameter("postIdx", postIdx)
                    .setParameter("userIdx", userPrincipal.getIdx());
        }

        try {
            postViewEntity = query
                    .getSingleResult();
            postViewEntity.setUpdateTime(new Date());

            em.merge(postViewEntity);
        } catch (NoResultException e) {
            postViewEntity = new PostViewEntity();
            postViewEntity.setPostIdx(postIdx);
            postViewEntity.setUserIdx(userPrincipal == null ? null : userPrincipal.getIdx());
            postViewEntity.setSessionId(sessionId);
            postViewEntity.setViewerType(userPrincipal == null ? PostViewEntity.ViewerType.SESSION : PostViewEntity.ViewerType.USER);

            em.persist(postViewEntity);

            long count = em.createQuery("select count(v.postIdx) from PostViewEntity v where v.postIdx = :postIdx", Long.class)
                    .setParameter("postIdx", postIdx)
                    .getSingleResult();

            postRepository.updatePostViewerCount(postIdx, (int) count);
        }
    }
}
