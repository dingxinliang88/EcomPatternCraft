package com.youyi.ecom.controller;


import com.youyi.ecom.pojo.po.User;
import com.youyi.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(String username, String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }
}
