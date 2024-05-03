package com.example.DemoSecurity.mapper;

import com.example.DemoSecurity.dto.UserDto;
import com.example.DemoSecurity.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper extends AbstractMapper<User, UserDto> {
    public UserMapper() {
        super(User.class, UserDto.class);
    }
}
