package com.x3110.learningcommunity.controller;

import com.x3110.learningcommunity.model.User;
import com.x3110.learningcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="addPost", method = RequestMethod.POST)
    public int addPost(@RequestBody User user) {
        return userService.addUser(user);
    }
}
