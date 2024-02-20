package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    @PostMapping ("/{username}/{postId:[\\d]+}")
    public String addPost(@PathVariable("username") String username, @PathVariable("postId") Long postId, @ModelAttribute("body") String body, Principal principal) {
        commentService.AddCommentToPost(principal, postId, body);
        return "redirect:/" + username + "/" + postId;
    }

    /**

    @DeleteMapping(path = "comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.DeleteComment(commentId);
        return ResponseEntity.ok("Comment deleted");
    }

    **/
}
