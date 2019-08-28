package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
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
    public int addComment(Comment comment) {
        // Post fatherPost=postService.findPostById(comment.getFatherPostId());
        // fatherPost.getCommentArrayList().add(comment);
        //comment.setNo(fatherPost.getCommentArrayList().size());
        Query query = new Query(Criteria.where("id").is(comment.getFatherId()));
        Update update = new Update();
        comment.setCreatedDate(LocalDateTime.now());
        Post post = postService.findPostById(comment.getFatherId());
        List<Comment> comments = post.getComment();
        if (comments == null) {
            comment.setNo(1);
        } else
            comment.setNo(post.getComment().size() + 1);
        //System.out.println(post.getComment());

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
    public Result addLike(Comment comment) {
        String fatherId = comment.getFatherId();
        Query query = new Query(Criteria.where("id").is(fatherId));
        Post post = mongoTemplate.findOne(query, Post.class);
        if (post == null) return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        int index = comment.getNo() - 1;
        Comment comment1 = post.getComment().get(index);
        if (comment1 == null) return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        comment1.setLikeNum(comment1.getLikeNum() + 1);
        return ResultFactory.buildSuccessResult("点赞成功");
    }
}
