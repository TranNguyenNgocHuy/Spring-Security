package com.example.DemoSecurity.response;

import com.example.DemoSecurity.dto.UserDto;
import lombok.Data;

@Data
public class UserResponseDto extends ResponseDto<UserDto> {
    private String accessToken;
    private String tokenType;
}
