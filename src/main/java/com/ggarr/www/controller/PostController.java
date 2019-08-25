package com.ggarr.www.controller;

import com.ggarr.www.core.config.security.UserPrincipal;
import com.ggarr.www.entity.CommentEntity;
import com.ggarr.www.entity.PostEntity;
import com.ggarr.www.entity.ReactionEntity;
import com.ggarr.www.service.CommentService;
import com.ggarr.www.service.PostService;
import com.ggarr.www.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReactionService reactionService;

    @PreAuthorize("hasAnyAuthority('BASIC')")
    @GetMapping(value = "/create")
    public String createPage(Model model) {
        PostEntity post = new PostEntity();
        model.addAttribute("post", post);
        return "post-create";
    }

    @PreAuthorize("hasAnyAuthority('BASIC')")
    @PostMapping(value = "/create")
    public void savePost(HttpServletResponse response, PostEntity postEntity, @AuthenticationPrincipal UserPrincipal user) throws IOException {
        PostEntity entity = postService.save(postEntity, user);
        response.sendRedirect("/posts/" + entity.getIdx());
    }

    @PreAuthorize("hasAnyAuthority('BASIC')")
    @GetMapping(value = "/posts/{idx}/update")
    public String updatePage(
            @PathVariable Integer idx,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model
    ) {
        PostEntity postEntity = postService.findPost(idx);
        if (!userPrincipal.getIdx().equals(postEntity.getCreateUser().getIdx())) {
            return "redirect:/posts/" + idx;
        }
        model.addAttribute("post", postEntity);
        return "post-create";
    }

    @GetMapping(value = "/posts/{idx}")
    public String postDetail(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PageableDefault(sort = "idx", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable Integer idx,
            Model model
    ) {
        PostEntity entity = postService.findPost(idx);

        if (entity == null) {
            return "redirect:/";
        }

        CommentEntity comment = new CommentEntity();
        Page<CommentEntity> commentList = commentService.findCommentListByPost(idx, pageable);
        ReactionEntity reactionEntity = reactionService.findReaction(idx, userPrincipal == null ? null : userPrincipal.getIdx());

        model.addAttribute("post", entity);
        model.addAttribute("reaction", reactionEntity);
        model.addAttribute("like", reactionService.countReactionByPost(idx, ReactionEntity.ReactionType.LIKE));
        model.addAttribute("sad", reactionService.countReactionByPost(idx, ReactionEntity.ReactionType.SAD));
        model.addAttribute("no", reactionService.countReactionByPost(idx, ReactionEntity.ReactionType.NO));
        model.addAttribute("unbelieve", reactionService.countReactionByPost(idx, ReactionEntity.ReactionType.UNBELIEVE));
        model.addAttribute("userPrincipal", userPrincipal);
        model.addAttribute("comment", comment);
        model.addAttribute("commentList", commentList);
        return "post-detail";
    }

    @PreAuthorize("hasAnyAuthority('BASIC')")
    @PostMapping(value = "/posts/{postIdx}/comment")
    public void createComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Integer postIdx,
            HttpServletResponse response,
            CommentEntity comment
    ) throws IOException {
        CommentEntity entity = commentService.save(comment, postIdx, userPrincipal.getIdx());
        response.sendRedirect("/posts/" + postIdx);
    }

    @PostMapping(value = "/comment/delete")
    public void removeComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam Integer postIdx,
            @RequestParam Integer commentIdx,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        commentService.deleteComment(commentIdx, userPrincipal);
        response.sendRedirect("/posts/" + postIdx);
    }
}
