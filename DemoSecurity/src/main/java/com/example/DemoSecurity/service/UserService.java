package com.example.DemoSecurity.service;

import com.example.DemoSecurity.dto.UserDto;

public interface UserService {
    UserDto register(UserDto userDto);

    String login(UserDto userDto);
}
