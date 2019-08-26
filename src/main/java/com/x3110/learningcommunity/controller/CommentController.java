package com.x3110.learningcommunity.controller;

import com.x3110.learningcommunity.model.Comment;
import com.x3110.learningcommunity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value="addComment", method = RequestMethod.POST)
    public int addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @RequestMapping(value = "removeComment",method = RequestMethod.POST)
    public int removeComment(@RequestBody Comment comment){return  commentService.removeComment(comment);}

    @RequestMapping(value = "updateComment",method = RequestMethod.POST)
    public int updateComment(@RequestBody Comment comment){return  commentService.updateComment(comment);}
}
