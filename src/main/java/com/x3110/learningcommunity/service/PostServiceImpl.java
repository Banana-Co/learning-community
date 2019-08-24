package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@Primary
public class PostServiceImpl implements PostService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public int addPost(Post post) {
        mongoTemplate.save(post);

        return 1;
    }
}
