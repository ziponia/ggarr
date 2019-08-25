package com.ggarr.www.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "tbl_reaction")
public class ReactionEntity implements Serializable {

    @Id
    private Integer userId;

    @Id
    private Integer postId;

    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    public enum ReactionType {
        LIKE, // 좋아욧
        NO, // 싫어욧
        UNBELIEVE, // 기가막혀욧
        SAD // 슬퍼욧
    }
}
