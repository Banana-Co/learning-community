package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.x3110.learningcommunity.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    int addPost(Post post);
    DeleteResult removePost(String id);
    int updatePost(Post post);
    Post findPostById(String id);
    Page<Post> findPostByPage(Integer page, String sortedby, String order);
    List<Post> MyPost(String author);
    UpdateResult updateComments(Post post);
    Page<Post> findPostByAuthorAndPage(String author, Integer page, String sortedby, String order);
    Page<Post> findThreadPostByPage(Integer page, String sortedby, String order, int threadId);
}
