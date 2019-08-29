package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.User;
import com.x3110.learningcommunity.result.Result;
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

    void uploadAvater(User user);

    DeleteResult deleteUser(String username);

    void notify(String username1, String username2, String message, int type);

    Result readNotification(String username, int notiNo);
}
