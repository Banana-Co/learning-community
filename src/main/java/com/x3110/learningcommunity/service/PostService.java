package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Post;

public interface PostService {
    int addPost(Post post);
    int removePost(Long postId);
    int updatePost(Post post);
    Post findPostById(Long postId);
}
