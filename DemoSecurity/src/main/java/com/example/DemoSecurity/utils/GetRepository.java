package com.example.DemoSecurity.utils;

import com.example.DemoSecurity.entity.User;
import com.example.DemoSecurity.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GetRepository<T> {
    private final Map<Class<?>, JpaRepository<?,?>> repositoryMap = new HashMap<>();

    private UserRepository userRepository;

    public GetRepository(UserRepository userRepository) {
        repositoryMap.put(User.class, userRepository);
    }

    public JpaRepository<?, ?> get(Class<?> entityClazz) {
        return repositoryMap.get(entityClazz);
    }
}
