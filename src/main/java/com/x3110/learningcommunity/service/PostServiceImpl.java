package com.x3110.learningcommunity.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.model.PostRepository;
import com.x3110.learningcommunity.result.Result;
import com.x3110.learningcommunity.util.MongoAutoId;


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
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Primary
public class PostServiceImpl implements PostService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PostRepository postRepository;

    @Override
    public int addPost(Post post) {
       //MongoAutoId mongoAutoId=new MongoAutoId();
       // post.setPostId(mongoAutoId.getNextSequence("post"));
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
        Query query=new Query(Criteria.where("postId").is(post.getPostId()));
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
    public List<Post> findPostByKeyword(String keyword) {
        Pattern pattern = Pattern.compile(".*?" + escapeExprSpecialWord(keyword) + ".*");
        Query query=Query.query(Criteria.where("title").regex(pattern));
        List<Post> posts=mongoTemplate.find(query,Post.class);
        return posts;
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
        System.out.println(keyword);
        return keyword;

    }
}
