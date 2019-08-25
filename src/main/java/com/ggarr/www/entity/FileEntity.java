package com.ggarr.www.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@Table(name = "tbl_file")
public class FileEntity {

    @Id
    @GeneratedValue
    private Integer idx;

    private String filePath;

    private String fileName;

    private String originalName;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity uploader;

    @ManyToOne(targetEntity = PostEntity.class)
    private PostEntity postEntity;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private Long fileSize;

    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    public enum FileType {
        IMAGE,
        FILE
    }
}
