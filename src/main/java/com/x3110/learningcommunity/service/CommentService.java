package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Comment;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);
    List<Comment> findComment(String fatherId);
    int removeComment(Comment comment);
    int updateComment(Comment comment);
}
