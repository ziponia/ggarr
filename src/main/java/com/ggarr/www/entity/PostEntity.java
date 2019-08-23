package com.ggarr.www.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

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

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity createUser;

    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;
}
