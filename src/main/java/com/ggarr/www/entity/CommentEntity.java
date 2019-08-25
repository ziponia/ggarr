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
    private Integer idx;

    private String content;

    @ManyToOne(targetEntity = PostEntity.class)
    private PostEntity post;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity createUser;



    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;
}
