package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.result.Result;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);
    List<Comment> findComment(String fatherId);
    DeleteResult removeComment(String id);
    int updateComment(Comment comment);
    Result addLike(String id);
}
