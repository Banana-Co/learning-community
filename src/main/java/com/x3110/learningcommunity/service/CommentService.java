package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Comment;

public interface CommentService {
    int addComment(Comment comment);
    int removeComment(Comment comment);
    int updateComment(Comment comment);
}
