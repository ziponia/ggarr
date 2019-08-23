package com.ggarr.www.core.config.security;

import com.ggarr.www.entity.UserEntity;
import com.ggarr.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("유저를 찾을 수 없습니다.");
        }

        return new UserPrincipal(userEntity.getIdx(), userEntity.getEmail(), userEntity.getPassword(), AuthorityUtils.createAuthorityList("BASIC"));
    }
}
