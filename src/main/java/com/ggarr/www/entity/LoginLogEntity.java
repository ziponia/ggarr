package com.ggarr.www.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tbl_log_login")
public class LoginLogEntity {

    @Id
    @GeneratedValue
    private Integer idx;

    private String name;
    private Integer userPk;

    private String remoteAddr;
    private String agent;
    private String referer;
    private boolean success;

    @CreationTimestamp
    private Date createTime;

    public void setDefaultEntity(HttpServletRequest request, HttpServletResponse response) {
        this.agent = request.getHeader("User-Agent");
        this.remoteAddr = request.getRemoteAddr();
        this.referer = request.getHeader("Referer");
    }
}
