package com.example.DemoSecurity.controller;

import com.example.DemoSecurity.consts.ApiPath;
import com.example.DemoSecurity.dto.UserDto;
import com.example.DemoSecurity.response.UserResponseDto;
import com.example.DemoSecurity.service.Impl.UserServiceImpl;
import com.example.DemoSecurity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Slf4j
@Controller
public class UserController {
    @Autowired
    UserServiceImpl service;

    @GetMapping(value = ApiPath.USER_GET_ALL)
    public ResponseEntity<?> getAllUsers() {
        UserResponseDto response = new UserResponseDto();
        try {
            UserDto userDto = new UserDto();
            List<UserDto> list = service.getAll(userDto);
            response.setList(list);
            response.setMessage("Success get all users");
            response.setErrorCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Error when get all users:" + e);
            response.setErrorCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.USER_REGISTER)
    public ResponseEntity<UserResponseDto> register(@RequestBody UserDto userDto) {
        UserResponseDto response = new UserResponseDto();
        try {
            UserDto result = service.register(userDto);
            if (result != null) {
                response.setMessage("Register Successful ");
                response.setErrorCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setMessage("User name already exist !");
            response.setErrorCode(409);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            response.setMessage("Error when register:" + e);
            response.setErrorCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = ApiPath.USER_LOGIN)
    public ResponseEntity<UserResponseDto> signIn(@RequestBody UserDto userDto) {
        UserResponseDto response = new UserResponseDto();
        try {
            String token = service.login(userDto);
            if (token != null) {
                response.setAccessToken(token);
                response.setMessage("Successful sign in");
                response.setErrorCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setMessage("UserName or password incorrect!!");
            response.setErrorCode(401);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            response.setMessage("Error when signIn:" + e);
            response.setErrorCode(500);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
