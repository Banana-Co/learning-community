package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 修改密码
     * @param user
     */
    void changePswd(User user);

    /**
     * 通过用户名查找用户
     * @param username
     * @return User
     */
    User getUserByUsername(String username);

    /**
     * 通过id查找用户
     * @param user_name
     * @return Integer
     */
    Integer getIdByName(String user_name);

}
