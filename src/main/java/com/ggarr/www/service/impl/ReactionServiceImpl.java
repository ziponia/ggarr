package com.ggarr.www.service.impl;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.ReactionEntity;
import com.ggarr.www.repository.PostRepository;
import com.ggarr.www.repository.UserRepository;
import com.ggarr.www.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Service
public class ReactionServiceImpl implements ReactionService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public ReactionEntity userReaction(Integer postIdx, ReactionEntity.ReactionType reactionType, UserPrincipal userPrincipal) {
        ReactionEntity reactionEntity = findReactionQuery(postIdx, userPrincipal.getIdx());

        if (reactionEntity == null) {
            reactionEntity = new ReactionEntity();
        }

        if (reactionEntity.getReactionType() != null && reactionEntity.getReactionType().equals(reactionType)) {
            em.remove(reactionEntity);
            return null;
        }

        reactionEntity.setPostId(postIdx);
        reactionEntity.setUserId(userPrincipal.getIdx());
        reactionEntity.setReactionType(reactionType);
        em.persist(reactionEntity);
        return reactionEntity;
    }

    @Override
    public ReactionEntity findReaction(Integer postIdx, Integer userIdx) {
        return findReactionQuery(postIdx, userIdx);
    }

    private ReactionEntity findReactionQuery(Integer postIdx, Integer userIdx) {
        try {
            return em.createQuery("select vo from ReactionEntity vo where vo.postId = :postId and vo.userId = :userId", ReactionEntity.class)
                    .setParameter("postId", postIdx)
                    .setParameter("userId", userIdx)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public long countReactionByPost(Integer postIdx, ReactionEntity.ReactionType type) {
        try {
            return em.createQuery("select count(vo.postId) from ReactionEntity vo where vo.postId = :postIdx and vo.reactionType = :reactionType", Long.class)
                    .setParameter("postIdx", postIdx)
                    .setParameter("reactionType", type)
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }
}
