package com.example.DemoSecurity.dto;

import com.example.DemoSecurity.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.Set;


@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Data
public class UserDto {

    private String uuid;
    private String fullName;
    private String email;
    private String userName;
    private String password;
    private Set<String> roles;
}
