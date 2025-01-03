package com.example.dolly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/h")
    String hello() {
        return "hello world!";
    }

    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private PasswordEncoder encoder;
    @PostMapping("/signup")
    String signup(@RequestBody SignupRq dto) {
        if (userDetailsManager.userExists(dto.username())) {
            return "新增使用者失敗" + dto.username() + "已存在";
        }
        userDetailsManager.createUser(User.builder()
                .username(dto.username())
                .password(encoder.encode(dto.password()))
                .roles("USER")
                .build());
        return "成功建立使用者" + dto.username();
    }

    record SignupRq(
            String username,
            String password
    ) {
    }
}
