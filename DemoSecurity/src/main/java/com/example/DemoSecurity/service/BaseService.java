package com.example.DemoSecurity.service;

import java.util.List;

public interface BaseService<T> {
    T create(T dto);
    T getByID(T dto);
    List<T> getAll(T dto);
    T update(T dto);
    void delete(T dto);
}
