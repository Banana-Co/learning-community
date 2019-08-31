package com.x3110.learningcommunity.service;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.model.User;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Primary
public class CommentServiceImpl implements CommentService {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    @Override
    public Result removeComment(String fathreId, int no){
        Post post = postService.findPostById(fathreId);
        List<Comment> comments = post.getComment();
        if(comments == null)return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        Comment target = null;
        for(Comment comment : comments){
            if(comment.getNo() == no) target = comment;
        }
        if(target == null)return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);

        comments.remove(target);
        postService.updateComments(post);
        return ResultFactory.buildSuccessResult("删除成功！");
    }

    @Override
    public UpdateResult addComment(Comment comment) {
        Query query=new Query(Criteria.where("id").is(comment.getFatherId()));
        Update update = new Update();
        comment.setCreatedDate(LocalDateTime.now());
        Post post = postService.findPostById(comment.getFatherId());
        List<Comment> comments = post.getComment();
        if (comments == null) {
            comment.setNo(0);
        } else{
            comment.setNo(post.getReplyNum() + 1);
            //notify
            Comment comment1 = findCommentByNo(comment.getFatherId(),comment.getFatherNo());//被评论的评论
            String message = "评论了你的帖子\"" + comment1.getContent()+"\"";
            userService.notify(comment.getAuthor(), comment1.getAuthor(),message,2, comment.getFatherId());
        }
        update.set("replyNum",post.getReplyNum()+1);
        update.set("latestReplyDate", comment.getCreatedDate());
        update.addToSet("comment", comment);
        return mongoTemplate.updateFirst(query, update, Post.class);
    }

    @Override
    public List<Comment> findComment(String fatherId) {
        Post post = mongoTemplate.findById(fatherId, Post.class);
        if(post.getComment() == null) return null;
        return post.getComment();
    }

    @Override
    public Comment findCommentByNo(String fatherId, int no){
        return findComment(fatherId).get(no);
    }

    @Override
    public Result addLike(String fatherId, int no, String username) {
        Post post = postService.findPostById(fatherId);//找到father psot
        if (post == null) return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);

        Comment comment = post.getComment().get(no);//根据楼层找到comment
        if (comment == null) return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        String username2 = comment.getAuthor();//接收通知的用户
        userService.updatePrestige(username2, 1);//声望+1
        String message = "点赞了你的帖子\"" + comment.getContent()+"\"";

        List<String> likedUsers = comment.getLikeUsers();
        if(likedUsers == null){
            //notify
            userService.notify(username, username2, message, 1, fatherId);

            List<String> users = new ArrayList<>();
            users.add(username);
            comment.setLikeUsers(users);
            comment.setLikeNum(comment.getLikeNum() + 1);
        }else if(likedUsers.contains(username)){
            comment.getLikeUsers().remove(username);
            comment.setLikeNum(comment.getLikeNum() - 1);
            postService.updateComments(post);
            return ResultFactory.buildFailResult(ResultCode.HaveExist);
        }else {
            //notify
            userService.notify(username, username2, message, 1, fatherId);

            comment.getLikeUsers().add(username);
            comment.setLikeNum(comment.getLikeNum() + 1);
        }
        postService.updateComments(post);
        return ResultFactory.buildSuccessResult("点赞成功");
    }

    @Override
    public Result haveLiked(String fatherId, int no, String username) {
        Post post = postService.findPostById(fatherId);
        if(post == null)return ResultFactory.buildFailResult(ResultCode.NOT_FOUND);
        Comment comment = post.getComment().get(no);

        List<String> likedUsers = comment.getLikeUsers();
        if(likedUsers == null)return ResultFactory.buildFailResult(ResultCode.HaventLiked);//未点赞
        else if(likedUsers.contains(username))return ResultFactory.buildFailResult(ResultCode.HavaLiked);//已点赞
        else return ResultFactory.buildFailResult(ResultCode.HaventLiked);//未点赞
    }


}
