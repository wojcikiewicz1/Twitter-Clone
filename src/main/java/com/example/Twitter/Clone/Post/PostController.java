package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

/**
    @GetMapping (path = "/{username}/{postId}")
    public String getPostById (@PathVariable("postId") Long postId, @PathVariable("username") String username) {

        postService.getPostById(postId);
        return "post";
    }



    @PostMapping (path = "/posts/share")
    public ResponseEntity sharePost(@RequestHeader("username") String username, @RequestBody Long postId) {
        postService.sharePost(username, postId);
        return ResponseEntity.ok("Post shared");
    }

    @DeleteMapping (path = "/posts/{postId}")
    public ResponseEntity deletePost (@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Post deleted");
    }

    **/
}
