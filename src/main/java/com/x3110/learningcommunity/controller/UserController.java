package com.x3110.learningcommunity.controller;

import com.x3110.learningcommunity.model.User;
import com.x3110.learningcommunity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @CrossOrigin
    @RequestMapping(value="addPost", method = RequestMethod.POST)
    public int addPost(@RequestBody User user) {
        return userService.addUser(user);
    }
}
