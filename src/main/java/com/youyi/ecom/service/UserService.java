package com.youyi.ecom.service;

import com.youyi.ecom.pojo.po.User;
import com.youyi.ecom.repo.UserRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String login(String username, String password) {
        User user = userRepository.findByNameAndPwd(username, password);
        if (Objects.isNull(user)) {
            return "error username or password";
        }

        return "login success";
    }

    public String register(User user) {
        User userFromDb = userRepository.findByName(user.getName());
        if (Objects.nonNull(userFromDb)) {
            return "user already exists";
        }

        user.setCreateTime(LocalDateTime.now());
        userRepository.save(user);

        return "register success";
    }
}
