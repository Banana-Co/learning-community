package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.result.Result;
import org.springframework.data.domain.Page;

import javax.security.auth.login.Configuration;
import java.util.List;

public interface CommentService {
    UpdateResult addComment(Comment comment);
    List<Comment> findComment(String fatherId);
    Result addLike(String fatherId, int no, String username);
    Result haveLiked(String fatherId, int no, String username);
}
