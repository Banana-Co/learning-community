package com.x3110.learningcommunity.controller;

import com.mongodb.Mongo;
import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.model.Post;
import com.x3110.learningcommunity.model.PostRepository;
import com.x3110.learningcommunity.service.CommentService;
import com.x3110.learningcommunity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
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

    @CrossOrigin
    @RequestMapping(value="addPost", method = RequestMethod.POST)
    public int addPost(@RequestBody Post post) {
        Comment comment = new Comment();
        comment.setContent(post.getContent());
        comment.setAuthor(post.getAuthor());
        comment.setFatherId(post.getId());
        commentService.addComment(comment);
        return postService.addPost(post);
    }

    @RequestMapping(value="addPosts", method = RequestMethod.POST)
    public int addPosts(@RequestBody List<Post> posts) {
        for (Post post : posts) {
            postService.addPost(post);
        }
        return 1;
    }

    @CrossOrigin
    @RequestMapping(value = "removePost/id={id}", method = RequestMethod.POST)
    public DeleteResult removePost(@PathVariable (value = "id") String id){
        return postService.removePost(id);
    }

    @RequestMapping(value = "updatePost",method = RequestMethod.POST)
    public int updatePost(@RequestBody Post post){return postService.updatePost(post);}

    @CrossOrigin
    @RequestMapping(value = "findPostById={id}",method = RequestMethod.GET)
    public Post findPostById(@PathVariable (name="id")String id){return postService.findPostById(id);}

    @RequestMapping(value = "getPostByPage", method = RequestMethod.GET)
    public Page<Post> getPostByPage(@RequestParam Integer page, @RequestParam String sortedby, @RequestParam String order) {
        return postService.findPostByPage(page - 1, sortedby, order);
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value ="findPostByKeyword={keyword}",method = RequestMethod.GET)
    public List<Post> findPostByKeyword(@PathVariable (value = "keyword")String keyword){
        return postService.findPostByKeyword(keyword);
    }

}
