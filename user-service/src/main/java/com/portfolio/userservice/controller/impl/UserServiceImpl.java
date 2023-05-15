package com.portfolio.userservice.controller.impl;

import com.portfolio.userservice.model.user.Role;
import com.portfolio.userservice.model.user.UserEntity;
import com.portfolio.userservice.model.user.UserRepository;
import com.portfolio.userservice.model.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity;

        if (username.contains("@")) {
            userEntity = userRepository.findByEmail(username).orElse(null);
        } else {
            userEntity = userRepository.findByUsername(username).orElse(null);
        }
        if (userEntity == null) {
            throw new UsernameNotFoundException(username + "의 유저를 찾을 수 없습니다.");
        }
        Collection<? extends GrantedAuthority> authorities = userEntity.getAuthorities();

        return new User(userEntity.getEmail(), userEntity.getPassword(), true, true, true, true,
                authorities);
    }

}
