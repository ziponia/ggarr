package com.ggarr.www.core.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserPrincipal extends User {

    private Integer idx;

    public UserPrincipal(Integer idx, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idx = idx;
    }

    public Integer getIdx() {
        return idx;
    }
}
