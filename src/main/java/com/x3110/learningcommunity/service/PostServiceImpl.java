package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    @Override
    public int removePost(Long postId){
        mongoTemplate.remove(new Query(Criteria.where("id").is(findPostById(postId).getId())));
        return 1;
    }

    @Override
    public int updatePost(Post post){
        Query query=new Query(Criteria.where("PostId").is(post.getPostId()));
        Update update=new Update();
        update.set("title",post.getTitle());
        update.set("content",post.getContent());
        update.set("createDate",post.getCreatedDate());

        mongoTemplate.updateFirst(query,update,Post.class);

        return 1;
    }

    @Override
    public Post findPostById(Long postId){
        Query query=new Query(Criteria.where("postId").is(postId));
        Post post=mongoTemplate.findOne(query,Post.class);
        return post;
    }

}
