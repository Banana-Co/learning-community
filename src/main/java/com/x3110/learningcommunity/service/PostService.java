package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Post;

public interface PostService {
    int addPost(Post post);
    int removePost(String id);
    int updatePost(Post post);
    Post findPostById(String id);
}
