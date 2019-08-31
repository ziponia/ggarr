package com.ggarr.www.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_post")
@Getter
@Setter
public class PostEntity {

    @Id
    @GeneratedValue
    private Integer idx;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("true")
    private boolean publish = true;

    @ColumnDefault("0")
    private int viewCount;

    @ColumnDefault("0")
    private int commentCount;

    @ColumnDefault("0")
    private int reactionCount;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity createUser;

    @OneToMany(targetEntity = CommentEntity.class, mappedBy = "post")
    private List<CommentEntity> comments;

    @OneToMany(targetEntity = ReactionEntity.class, mappedBy = "postId")
    public List<ReactionEntity> reactions;

    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    public int getCommentCount() {
        return comments.size();
    }

    public int getReactionCount() {
        return reactions.size();
    }

    public String getPostContentRemoveHTML() {
        Document doc = Jsoup.parse(content);
        return doc.text();
    }
}
