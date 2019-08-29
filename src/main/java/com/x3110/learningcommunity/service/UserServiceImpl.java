package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Primary
public class UserServiceImpl implements UserService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public int addUser(User user) {
        user.setCreatedDate(LocalDateTime.now());
        mongoTemplate.insert(user);
        return 1;
    }



    @Override
    public void changePswd(User user) {
        mongoTemplate.save(user);//可能存在稳定性的问题，后期考虑是否修改。
    }

    @Override
    public User getUserByUsername(String username) {
        Query query=new Query(Criteria.where("username").is(username));
        User user=mongoTemplate.findOne(query,User.class);
        return user;
    }

    @Override
    public void uploadAvater(User user) {
        mongoTemplate.save(user);//可能存在稳定性问题，后期考虑是否修改。
    }

    @Override
    public DeleteResult deleteUser(String username) {
        return mongoTemplate.remove(new Query(Criteria.where("username").is(username)),"user");
    }

    @Override
    public void notify(String username) {

    }
}
