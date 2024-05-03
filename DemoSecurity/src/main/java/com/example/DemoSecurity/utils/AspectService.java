package com.example.DemoSecurity.utils;

import lombok.Data;
import org.aspectj.lang.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Data
@Aspect
public class AspectService<T> {
    private GetRepository getRepositoryService;
    private GetEntity getEntityService;
    private GetPrimaryKey getPrimaryKey;
    private ModelMapper modelMapper;
    //
    private JpaRepository<?, ?> repository;
    private Object entity;
    private Object valueId;

    public AspectService(GetRepository getRepositoryService
                    , GetEntity getEntityService
                    , GetPrimaryKey getPrimaryKey
                    , ModelMapper modelMapper) {
        this.getRepositoryService = getRepositoryService;
        this.getEntityService = getEntityService;
        this.getPrimaryKey = getPrimaryKey;
        this.modelMapper = modelMapper;
    }

    // @Before("execution(* com.example.DemoSecurity.service.Impl.AbstractBaseServiceImpl.*(..)) && args(dto,...)")

    @Before("execution(* com.example.DemoSecurity.service.Impl.AbstractBaseServiceImpl.create(..)) && args(dto)")
    public void beforeCreate(T dto) {
        this.entity = getEntityService.getByObject(dto);
        this.repository = getRepositoryService.get(this.entity.getClass());
    }

    @Before("execution(* com.example.DemoSecurity.service.Impl.AbstractBaseServiceImpl.update(..)) && args(dto)")
    public void beforeUpdate(T dto) {
        this.entity = getEntityService.getByObject(dto);
        this.repository = getRepositoryService.get(this.entity.getClass());
    }

    @Before("execution(* com.example.DemoSecurity.service.Impl.AbstractBaseServiceImpl.getAll(..)) && args(dto)")
    public void beforeGetAll(T dto) {
        this.entity = getEntityService.getByObject(dto);
        this.repository = getRepositoryService.get(this.entity.getClass());
    }

    @Before("execution(* com.example.DemoSecurity.service.Impl.AbstractBaseServiceImpl.getByID(..)) && args(dto)")
    public void beforeGetById(T dto) {
        this.entity = getEntityService.getByObject(dto);
        this.repository = getRepositoryService.get(this.entity.getClass());
        this.valueId = getPrimaryKey.getPrimaryKey(modelMapper.map(dto, entity.getClass()));
        System.out.println(valueId);
    }

    @Before("execution(* com.example.DemoSecurity.service.Impl.AbstractBaseServiceImpl.delete(..)) && args(dto)")
    public void beforeDelete(T dto) {
        this.entity = getEntityService.getByObject(dto);
        this.repository = getRepositoryService.get(this.entity.getClass());
        this.valueId = getPrimaryKey.getPrimaryKey(modelMapper.map(dto, entity.getClass()));
        System.out.println(valueId);
    }
}
