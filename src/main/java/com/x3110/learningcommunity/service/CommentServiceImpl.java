package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.result.ResultCode;
import com.x3110.learningcommunity.result.ResultFactory;
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
    public UpdateResult addComment(Comment comment) {
        Query query=new Query(Criteria.where("id").is(comment.getFatherId()));
        Update update = new Update();
        comment.setCreatedDate(LocalDateTime.now());
        Post post = postService.findPostById(comment.getFatherId());
        System.out.println(post);
        List<Comment> comments = post.getComment();
        if (comments == null) {
            comment.setNo(0);
        } else
            comment.setNo(post.getComment().size());
        //System.out.println(post.getComment());
        update.setOnInsert("lastedReplyDate", comment.getCreatedDate());
        update.addToSet("comment", comment);
        return mongoTemplate.updateFirst(query, update, Post.class);
    }

    @Override
    public List<Comment> findComment(String fatherId) {
        Post post = mongoTemplate.findById(fatherId, Post.class);
        return post.getComment();
    }

    @Override
    public Result addLike(Comment comment) {
        Post post = postService.findPostById(comment.getFatherId());
        if (post == null) return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        int index = comment.getNo() - 1;
        Comment comment1 = post.getComment().get(index);
        if (comment1 == null) return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        comment1.setLikeNum(comment1.getLikeNum() + 1);
        return ResultFactory.buildSuccessResult("点赞成功");
    }
    
}
