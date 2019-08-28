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
        List<Comment> comments = post.getComment();
        if (comments == null) {
            comment.setNo(0);
        } else
            comment.setNo(post.getComment().size());
        //System.out.println(post.getComment());
        update.set("replyNum",post.getReplyNum()+1);
        update.set("latestReplyDate", comment.getCreatedDate());
        update.addToSet("comment", comment);
        return mongoTemplate.updateFirst(query, update, Post.class);
    }

    @Override
    public List<Comment> findComment(String fatherId) {
        Post post = mongoTemplate.findById(fatherId, Post.class);
        return post.getComment();
    }

    @Override
    public Result addLike(String fatherId, int no, String username) {

        Post post = postService.findPostById(fatherId);//找到father psot
        if (post == null) return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);

        Comment comment = post.getComment().get(no);//根据楼层找到comment
        if (comment == null) return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        List<String> likedUsers = comment.getLikeUsers();
        if(likedUsers == null){
            comment.getLikeUsers().add(username);
            comment.setLikeNum(comment.getLikeNum() + 1);
        }else if(likedUsers.contains(username)){
            comment.setLikeNum(comment.getLikeNum() - 1);
            return ResultFactory.buildFailResult(ResultCode.HaveExist);
        }else {
            comment.getLikeUsers().add(username);
            comment.setLikeNum(comment.getLikeNum() + 1);
        }
        postService.updateComments(post);
        return ResultFactory.buildSuccessResult("点赞成功");
    }
    
}
