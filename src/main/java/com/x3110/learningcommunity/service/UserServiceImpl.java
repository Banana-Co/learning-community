package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImpl implements UserService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public int addUser(User user) {
        mongoTemplate.insert(user);
        return 1;
    }

    @Override
    public void changePswd(User user) {

    }

    @Override
    public Integer getIdByName(String user_name) {
        return null;
    }

    @Override
    public User getUser(String user_name) {
        return null;
    }
}
