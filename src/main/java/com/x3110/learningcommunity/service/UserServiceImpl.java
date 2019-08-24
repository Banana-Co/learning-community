package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
        mongoTemplate.save(user);//可能存在稳定性的问题，后期考虑修改。
    }

    @Override
    public Integer getIdByName(String user_name) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        Query query=new Query(Criteria.where("username").is(username));
        User user=mongoTemplate.findOne(query,User.class);
        return user;

    }

}
