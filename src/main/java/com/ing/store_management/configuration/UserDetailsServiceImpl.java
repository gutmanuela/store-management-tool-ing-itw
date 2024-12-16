package com.ing.store_management.configuration;

import com.ing.store_management.model.dto.UserDto;
import com.ing.store_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userService.getUserByUsername(username);
        return User.builder()
                .username(username)
                .password(userDto.getPassword())
                .roles(userDto.getRoleName())
                .build();
    }
}

