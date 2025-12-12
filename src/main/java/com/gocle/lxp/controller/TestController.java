package com.gocle.lxp.controller;

import com.gocle.lxp.domain.User;
import com.gocle.lxp.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final UserMapper userMapper;

    public TestController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/test")
    public User test(@RequestParam("id") Long id) {
        return userMapper.findById(id);
    }
}
