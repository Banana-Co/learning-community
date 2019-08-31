package com.x3110.learningcommunity.controller;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.model.PostRepository;
import com.x3110.learningcommunity.service.CommentService;
import com.x3110.learningcommunity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/post")
@CrossOrigin
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    PostRepository postRepository;

    @RequestMapping(value = "addPost", method = RequestMethod.POST)
    public UpdateResult addPost(@RequestBody Post post) {
        postService.addPost(post);
        Comment comment = new Comment();
        comment.setContent(post.getContent());
        comment.setAuthor(post.getAuthor());
        comment.setFatherId(post.getId());
        comment.setAvatarUrl(post.getAvatarUrl());
        return commentService.addComment(comment);
    }

    @RequestMapping(value = "addPosts", method = RequestMethod.POST)
    public int addPosts(@RequestBody List<Post> posts) {
        for (Post post : posts) {
            postService.addPost(post);
        }
        return 1;
    }

    @RequestMapping(value = "removePost", method = RequestMethod.GET)
    public DeleteResult removePost(@RequestParam String id) {
        return postService.removePost(id);
    }

    @RequestMapping(value = "updatePost", method = RequestMethod.POST)
    public int updatePost(@RequestBody Post post) {
        return postService.updatePost(post);
    }

    @RequestMapping(value = "findPostById={id}", method = RequestMethod.GET)
    public Post findPostById(@PathVariable(value = "id") String id) {
        return postService.findPostById(id);
    }

    @RequestMapping(value = "getPostByPage", method = RequestMethod.GET)
    public Page<Post> getPostByPage(@RequestParam Integer page, @RequestParam String sortedby, @RequestParam String order) {
        return postService.findPostByPage(page - 1, sortedby, order);
    }

    @RequestMapping(value = "getThreadPostByPage", method = RequestMethod.GET)
    public Page<Post> getThreadPostByPage(@RequestParam Integer page, @RequestParam String sortedby, @RequestParam String order, @RequestParam int threadId) {
        return postService.findThreadPostByPage(page - 1, sortedby, order, threadId);
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @RequestMapping(value = "findPostByKeyword={keyword}", method = RequestMethod.GET)
    public List<Post> findPostByKeyword(@PathVariable(value = "keyword") String keyword) {
        return postRepository.findByTitleLike(keyword);
    }

    @RequestMapping(value = "MyPost={author}", method = RequestMethod.GET)
    public List<Post> MyPost(@PathVariable(value = "author") String author) {
        return postService.MyPost(author);
    }

    @RequestMapping(value = "findPostByAuthorAndPage", method = RequestMethod.GET)
    public Page<Post> findPostByAuthorAndPage(@RequestParam String author, @RequestParam Integer page, @RequestParam String sortedby, @RequestParam String order) {
        return postService.findPostByAuthorAndPage(author, page - 1, sortedby, order);
    }
}
