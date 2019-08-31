package com.x3110.learningcommunity.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByTitleLike(String title);
    Page<Post> findByAuthor(String author, Pageable pageable);
    Page<Post> findByThreadId(int threadId, Pageable pageable);
}
