package com.x3110.learningcommunity.controller;

import com.mongodb.Mongo;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;

    @RequestMapping(value="addPost", method = RequestMethod.POST)
    public int addPost(@RequestBody Post post) {
        return postService.addPost(post);
    }

    @RequestMapping(value = "removePost",method = RequestMethod.POST)
    public int removePost(@RequestBody Long postId){return postService.removePost(postId);}

    @RequestMapping(value = "updatePost",method = RequestMethod.POST)
    public int updatePost(@RequestBody Post post){return postService.updatePost(post);}

    @CrossOrigin
    @RequestMapping(value = "findPostById={postId}",method = RequestMethod.GET)
    public Post findPostById(@PathVariable (name="postId")Long postId){return postService.findPostById(postId);}
}
