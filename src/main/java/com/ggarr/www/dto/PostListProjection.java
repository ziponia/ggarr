package com.ggarr.www.dto;

import lombok.Value;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Date;

@Value
public class PostListProjection {

    private Integer idx;

    private String title;

    private String content;
    private boolean publish = true;
    private int viewCount;
    private Date createTime;
    private Date updateTime;

    private int commentCount;

    private int reactionCount;

    public String getPostContentRemoveHTML() {
        Document doc = Jsoup.parse(content);
        return doc.text();
    }
}
