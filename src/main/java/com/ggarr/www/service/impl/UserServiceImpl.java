package com.ggarr.www.service.impl;

import com.ggarr.www.core.util.UtilComponent;
import com.ggarr.www.entity.UserEntity;
import com.ggarr.www.exception.handler.UserRegisterException;
import com.ggarr.www.repository.UserRepository;
import com.ggarr.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public boolean checkUserEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserEntity addUser(UserEntity userEntity) {
        if (userEntity.getEmail() == null || userEntity.getName() == null) {
            throw new UserRegisterException("Email 이랑 Name 은 필수!");
        }
        boolean check_email = !userRepository.existsByEmail(userEntity.getEmail());
        boolean check_name = !userRepository.existsByName(userEntity.getName());
        boolean check_password = userEntity.getPassword() != null && UtilComponent.passwordThen8AndSpecialCharacters(userEntity.getPassword());

        if (check_email && check_name && check_password) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            return userRepository.save(userEntity);
        }
        return null;
    }

    @Override
    public Authentication authenticateUser(String email, String password) {
        UserDetails details = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details.getUsername(), password, details.getAuthorities());
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkAllowUser(String email, String name, String password) {
        if (email == null || name == null) {
            return false;
        }

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            throw new UserRegisterException("제대로 된 이메일 이 아니에욧!");
        }

        boolean checkPassword = UtilComponent.passwordThen8AndSpecialCharacters(password);
        if (!checkPassword) {
            throw new UserRegisterException("비밀번호는 8자 이상 문자 숫자 특수문자 포함해서 해주세욧");
        }

        boolean check_email = !userRepository.existsByEmail(email);
        boolean check_name = !userRepository.existsByName(name);

        StringBuilder builder = new StringBuilder();

        if (!check_email) builder.append(email).append(" ");
        if (!check_name) builder.append(name).append(" ");

        if (!check_email || !check_name) {
            builder.append(" 은 사용중이에욧");
            throw new UserRegisterException(builder.toString());
        }

        return true;
    }
}