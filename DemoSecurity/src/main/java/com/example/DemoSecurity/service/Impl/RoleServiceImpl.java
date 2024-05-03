package com.example.DemoSecurity.service.Impl;

import com.example.DemoSecurity.dto.UserDto;
import com.example.DemoSecurity.service.RoleService;
import com.example.DemoSecurity.utils.AspectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends AbstractBaseServiceImpl<UserDto>
        implements RoleService {
    public RoleServiceImpl(AspectService aspect) {
        super(aspect);
    }
}
