package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.model.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.time.LocalDateTime;
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
        mongoTemplate.save(post);
        return 1;
    }

    @Override
    public DeleteResult removePost(String id){
        return mongoTemplate.remove(new Query(Criteria.where("_id").is(id)),"post");
    }

    @Override
    public int updatePost(Post post){
        Query query=new Query(Criteria.where("postId").is(post.getPostId()));
        Update update=new Update();
        update.set("title",post.getTitle());
        update.set("content",post.getContent());
        update.set("permission",post.getPermission());
        update.set("createdDate",new Date());
        mongoTemplate.updateFirst(query,update,Post.class);

        return 1;
    }

    @Override
    public UpdateResult updateComments(Post post){
        Query query=new Query(Criteria.where("id").is(post.getId()));
        Update update=new Update();
        update.set("comment",post.getComment());
        return mongoTemplate.updateFirst(query,update,Post.class);
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
    public Page<Post> findThreadPostByPage(Integer page, String sortedby, String order, int threadId){
        Sort sort;

        if (order.equals("asc"))
            sort = new Sort(Sort.Direction.ASC, sortedby);
        else if (order.equals("desc"))
            sort = new Sort(Sort.Direction.DESC, sortedby);
        else
            sort = new Sort(Sort.Direction.DESC, sortedby);

        Pageable pageable = PageRequest.of(page, 10, sort);

        return postRepository.findByThreadId(threadId, pageable);
    }

    @Override
    public List<Post> findPostWithFilter(String field, String value, String sortedby, String order, Integer days) {

        Query query = new Query(Criteria.where(field).is(value).and("createdDate").gte(getStartDate(days)));
        Sort sort;
        if (order.equals("asc"))
            sort = new Sort(Sort.Direction.ASC, sortedby);
        else
            sort = new Sort(Sort.Direction.DESC, sortedby);
        return mongoTemplate.find(query, Post.class);
    }

    private static LocalDateTime getStartDate(Integer days) {
        return LocalDateTime.now().minusDays(days);
    }

    @Override
    public Page<Post> findPostWithFilterAndPaging(String field, String value, String sortedby, String order, Integer days, int page, int size) {
        Query query = new Query(Criteria.where(field).is(value).and("createdDate").gte(getStartDate(days)));
        Sort sort;
        if (order.equals("asc"))
            sort = new Sort(Sort.Direction.ASC, sortedby);
        else
            sort = new Sort(Sort.Direction.DESC, sortedby);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = new PageImpl<Post>(mongoTemplate.find(query.with(pageable), Post.class), pageable, mongoTemplate.count(query, Post.class));
        return postPage;
    }

    String escapeExprSpecialWord(String keyword) {
        if (!StringUtils.isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        //System.out.println(keyword);
        return keyword;
    }

    @Override
    public List<Post> MyPost(String author){
        Query query=Query.query(Criteria.where("author").is(author));
        List<Post> posts=mongoTemplate.find(query,Post.class);
        return posts;
    }

    @Override
    public Page<Post> findPostByAuthorAndPage(String author, Integer page, Integer size, String sortedby, String order) {
        Pageable pageable;
        if (order.equals("asc"))
            pageable = PageRequest.of(page, size, new Sort(Sort.Direction.ASC, sortedby));
        else
            pageable = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, sortedby));

        return postRepository.findByAuthor(author, pageable);
    }
}
