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
@Table(name = "tbl_post_view")
public class PostViewEntity implements Serializable {

    private static final long serialVersionUID = -6634384894357808339L;

    @Id
    @GeneratedValue
    private Integer viewerIdx;

    private Integer postIdx;

    private Integer userIdx;

    private String sessionId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('SESSION', 'USER')")
    private ViewerType viewerType = ViewerType.SESSION;

    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    public enum ViewerType {
        SESSION,
        USER
    }
}
