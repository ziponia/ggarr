package com.ggarr.www.repository;

import com.ggarr.www.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    UserEntity findByEmail(String username);
}
