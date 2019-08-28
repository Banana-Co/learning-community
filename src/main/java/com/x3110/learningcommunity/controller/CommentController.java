package com.x3110.learningcommunity.controller;

import com.mongodb.client.result.DeleteResult;
import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value="addComment", method = RequestMethod.POST)
    public int addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @RequestMapping(value = "findCommentByFatherId={id}",method = RequestMethod.GET)
    public List<Comment> findCommentByFatherId(@PathVariable(name = "id") String id) {
        return commentService.findComment(id);
    }

    @CrossOrigin
    @RequestMapping(value = "removeComment/id={id}", method = RequestMethod.POST)
    public DeleteResult removeComment(@PathVariable (value = "id") String id){
        return commentService.removeComment(id);
    }

    @RequestMapping(value = "updateComment",method = RequestMethod.POST)
    public int updateComment(@RequestBody Comment comment){return  commentService.updateComment(comment);}
}
