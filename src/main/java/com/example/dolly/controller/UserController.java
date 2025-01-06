package com.example.dolly.controller;

import com.example.dolly.model.vo.UserVo;
import com.example.dolly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/get-all-user")
    public List<UserVo> getAllUser() {
        return userService.getUserList();
    }
}
