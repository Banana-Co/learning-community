package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.result.Result;

import javax.security.auth.login.Configuration;
import java.util.List;

public interface CommentService {
    int addComment(Comment comment);
    List<Comment> findComment(String fatherId);
    Result addLike(Comment comment);
}
