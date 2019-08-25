package com.x3110.learningcommunity.controller;

import com.mongodb.Mongo;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.model.PostRepository;
import com.x3110.learningcommunity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @RequestMapping(value="addPost", method = RequestMethod.POST)
    public int addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    @RequestMapping(value="addPosts", method = RequestMethod.POST)
    public int addPosts(@RequestBody List<Post> posts) {
        for (Post post : posts) {
            postService.addPost(post);
        }
        return 1;
    }

    @RequestMapping(value = "removePost",method = RequestMethod.POST)
    public int removePost(@RequestBody Long postId){return postService.removePost(postId);}

    @RequestMapping(value = "updatePost",method = RequestMethod.POST)
    public int updatePost(@RequestBody Post post){return postService.updatePost(post);}

    @CrossOrigin
    @RequestMapping(value = "findPostById={postId}",method = RequestMethod.GET)
    public Post findPostById(@PathVariable (name="postId")Long postId){return postService.findPostById(postId);}

    @RequestMapping(value = "getPostByPage", method = RequestMethod.GET)
    public Page<Post> getPostByPage(@RequestParam Integer page, @RequestParam String sortedby, @RequestParam String order) {
        return postService.findPostByPage(page - 1, sortedby, order);
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public List<Post> getAll() {
        return postRepository.findAll();
    }
}
