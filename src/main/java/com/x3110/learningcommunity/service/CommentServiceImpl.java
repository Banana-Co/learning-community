package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Primary
public class CommentServiceImpl implements CommentService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    PostService postService;

    @Override
    public int addComment(Comment comment) {
       // Post fatherPost=postService.findPostById(comment.getFatherPostId());
       // fatherPost.getCommentArrayList().add(comment);
       // comment.setNo(fatherPost.getCommentArrayList().size());
        mongoTemplate.insert(comment);
        return 1;
    }

    @Override
    public int removeComment(Comment comment){
        Query query=new Query(Criteria.where("id").is(comment.getId()));
        Update update=new Update();
        update.set("valid",0);
        update.set("createdDate",new Date());
        mongoTemplate.updateFirst(query,update,Comment.class);
        return 1;
    }

    @Override
    public int updateComment(Comment comment){
        Query query=new Query(Criteria.where("id").is(comment.getId()));
        Update update=new Update();
        update.set("content",comment.getContent());
        update.set("createdDate",new Date());
        mongoTemplate.updateFirst(query,update,Comment.class);
        return 1;
    }
}
