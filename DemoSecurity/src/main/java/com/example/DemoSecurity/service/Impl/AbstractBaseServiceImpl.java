package com.example.DemoSecurity.service.Impl;

import com.example.DemoSecurity.dto.UserDto;
import com.example.DemoSecurity.service.BaseService;
import com.example.DemoSecurity.utils.AspectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public abstract class AbstractBaseServiceImpl<T> implements BaseService<T> {
    private AspectService aspect;

    @Override
    public T create(T dto) {
        Object entity = aspect.getModelMapper().map(dto, aspect.getEntity().getClass());
        return (T) aspect.getModelMapper().map(aspect.getRepository().save(entity), dto.getClass());
    }

    @Override
    public T getByID(T dto) {
        return (T) aspect.getModelMapper().map(aspect.getRepository().findById(aspect.getValueId()), dto.getClass());
    }

    @Override
    public List<T> getAll(T dto) {
        List<T> entities = aspect.getRepository().findAll();
        return (List<T>) entities.stream().map((entity) -> aspect.getModelMapper().map(entity, dto.getClass())).toList();
    }

    @Override
    public T update(T dto) {
        Object entity = aspect.getModelMapper().map(dto, aspect.getEntity().getClass());
        return (T) aspect.getModelMapper().map(aspect.getRepository().save(entity), dto.getClass());
    }

    @Override
    public void delete(T dto) {
        aspect.getRepository().deleteById(aspect.getValueId());
    }

}
