package com.portfolio.userservice.model.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto getUserDetailsByEmail(String username);

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);
}
