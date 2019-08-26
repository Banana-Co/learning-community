package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    int addPost(Post post);
    int removePost(Post post);
    int updatePost(Post post);
    Post findPostById(String id);
    Page<Post> findPostByPage(Integer page, String sortedby, String order);
}
