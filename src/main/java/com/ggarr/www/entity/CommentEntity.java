package com.ggarr.www.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_comment")
@Getter
@Setter
public class CommentEntity {

    @Id
    @GeneratedValue
    protected Integer idx;

    protected String content;

    @JoinColumn(name = "reply")
    @ManyToOne(targetEntity = CommentEntity.class)
    protected CommentEntity commentReply;

    @ManyToOne(targetEntity = PostEntity.class)
    protected PostEntity post;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity createUser;

    @CreationTimestamp
    protected Date createTime;

    @UpdateTimestamp
    protected Date updateTime;
}
