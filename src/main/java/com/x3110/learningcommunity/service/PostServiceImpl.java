package com.x3110.learningcommunity.service;

import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.model.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Primary
public class PostServiceImpl implements PostService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PostRepository postRepository;

    @Override
    public int addPost(Post post) {
        mongoTemplate.insert(post);
        return 1;
    }

    @Override
    public int removePost(Post post){
        Query query=new Query(Criteria.where("postId").is(post.getPostId()));
        Update update=new Update();
        update.set("valid",0);
        update.set("createdDate",new Date());
        mongoTemplate.updateFirst(query,update,Post.class);
        return 1;
    }

    @Override
    public int updatePost(Post post){
        Query query=new Query(Criteria.where("postId").is(post.getPostId()));
        Update update=new Update();
        update.set("title",post.getTitle());
        update.set("content",post.getContent());
        update.set("createdDate",new Date());
        mongoTemplate.updateFirst(query,update,Post.class);

        return 1;
    }

    @Override
    public Post findPostById(String id){
        Query query=new Query(Criteria.where("id").is(id));
        Post post=mongoTemplate.findOne(query,Post.class);
        return post;
    }

    @Override
    public Page<Post> findPostByPage(Integer page, String sortedby, String order) {
        Sort sort;

        if (order.equals("asc"))
            sort = new Sort(Sort.Direction.ASC, sortedby);
        else if (order.equals("desc"))
            sort = new Sort(Sort.Direction.DESC, sortedby);
        else
            sort = new Sort(Sort.Direction.DESC, sortedby);

        Pageable pageable = PageRequest.of(page, 10, sort);

        return postRepository.findAll(pageable);
    }

    @Override
    public Post findPostByKeyword(String keyword) {
        return null;
    }
}
