package com.example.DemoSecurity.utils;

import com.example.DemoSecurity.dto.UserDto;
import com.example.DemoSecurity.entity.Role;
import com.example.DemoSecurity.entity.User;
import org.springframework.stereotype.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class GetEntity<T> {
    private final Map<Class<?>, Object> entities = new HashMap<>();

    private User user;

    public GetEntity(User user) {
        entities.put(UserDto.class, user);
    }

    public Object getByObject(T dto) {
        return entities.get(dto.getClass());
    }
}
