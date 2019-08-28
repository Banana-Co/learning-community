package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
        //comment.setNo(fatherPost.getCommentArrayList().size());
        Query query=new Query(Criteria.where("id").is(comment.getFatherId()));
        Update update = new Update();
        comment.setCreatedDate(LocalDateTime.now());
        update.setOnInsert("lastedReplyDate", comment.getCreatedDate());
        update.addToSet("comment", comment);
        update.inc("replyNum");
        mongoTemplate.updateFirst(query, update, Post.class);
        return 1;
    }

    @Override
    public List<Comment> findComment(String fatherId) {
        Post post = mongoTemplate.findById(fatherId, Post.class);
        return post.getComment();
    }

    @Override
    public DeleteResult removeComment(String id){
        return mongoTemplate.remove(new Query(Criteria.where("_id").is(id)),"comment");
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
