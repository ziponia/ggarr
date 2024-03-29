package com.ggarr.www.service;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.ReactionEntity;

public interface ReactionService {
    ReactionEntity userReaction(Integer postIdx, ReactionEntity.ReactionType reactionType, UserPrincipal userPrincipal);

    ReactionEntity findReaction(Integer postIdx, Integer userIdx);

    long countReactionByPostQuery(Integer postIdx, ReactionEntity.ReactionType type);

    long countReactionAllByPost(Integer postIdx);
}
