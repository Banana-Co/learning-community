package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.Comment;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);
    List<Comment> findComment(String fatherId);
    DeleteResult removeComment(String id);
    int updateComment(Comment comment);
}
