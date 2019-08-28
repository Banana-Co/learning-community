package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    int addPost(Post post);
    DeleteResult removePost(String id);
    int updatePost(Post post);
    Post findPostById(String id);
    Page<Post> findPostByPage(Integer page, String sortedby, String order);

   List<Post> findPostByKeyword(String keyword);
   List<Post> MyPost(String author);
}
