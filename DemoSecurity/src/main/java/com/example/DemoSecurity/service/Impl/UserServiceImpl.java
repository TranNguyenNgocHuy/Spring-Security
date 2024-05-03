package com.example.DemoSecurity.service.Impl;

import com.example.DemoSecurity.Enum.RoleName;
import com.example.DemoSecurity.dto.UserDto;
import com.example.DemoSecurity.entity.Role;
import com.example.DemoSecurity.entity.User;
import com.example.DemoSecurity.mapper.UserMapper;
import com.example.DemoSecurity.repository.RoleRepository;
import com.example.DemoSecurity.repository.UserRepository;
import com.example.DemoSecurity.security.jwt.JwtService;
import com.example.DemoSecurity.service.UserService;
import com.example.DemoSecurity.utils.AspectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends AbstractBaseServiceImpl<UserDto>
        implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper mapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    public UserServiceImpl(AspectService aspect) {
        super(aspect);
    }

    @Override
    public UserDto register(UserDto userDto) {
        UserDto result = new UserDto();

        try {
            if (userRepository.existsByUserName(userDto.getUserName())) {
                return null;
            }

            User user = new User();
            user.setFullName(userDto.getFullName());
            user.setEmail(userDto.getEmail());
            user.setUserName(userDto.getUserName());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            for (String roleName : userDto.getRoles()) {
                if (roleName.equals("ADMIN")) {
                    Role role = roleRepository.findByRoleName(RoleName.ADMIN);
                    user.getRoles().add(role);
                }
                if (roleName.equals("MANAGER")) {
                    Role role = roleRepository.findByRoleName(RoleName.MANAGER);
                    user.getRoles().add(role);
                }
                if (roleName.equals("USER")) {
                    Role role = roleRepository.findByRoleName(RoleName.USER);
                    user.getRoles().add(role);
                }
            }

            return mapper.convertEntityToDTO(userRepository.save(user));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String login(UserDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUserName(),
                        userDto.getPassword()
                )
        );

        User user = userRepository.findByUserName(userDto.getUserName()).orElseThrow();
        String token = jwtService.generateToken(user);
        System.out.println(user.getAuthorities());
        return token;
    }

}
