package com.ggarr.www.dto;

import com.ggarr.www.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

@Builder
@AllArgsConstructor
public class ReplyVo {

    @Getter
    private Integer idx;

    @Getter
    private String content;

    private UserEntity createUser;

    @Getter
    protected Date createTime;

    @Getter
    protected Date updateTime;

    public String getCreateUser() {
        return createUser.getName();
    }

    public Integer getCreateUserIdx() {
        return createUser.getIdx();
    }

    public boolean getAccessUpdate() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication != null && authentication.getName().equals(createUser.getEmail());
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
