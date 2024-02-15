package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PostController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

/**
    @GetMapping (path = "/username/{postId}")
    public Post getPostById (@PathVariable("postId") Long postId) {

        return postService.getPostById(postId);
    }

    @GetMapping (path = "/username")
    public List<Post> getAllPostsOfUser(String username) {
        User user = userService.findByUserName(username);
        List<Post> posts = postService.getPostsByUsername(user.getUsername());

        return posts;
    }



    @PostMapping (path = "/posts")
    public ResponseEntity addPost(@RequestHeader("username") String username, @RequestBody String postBody) {
        postService.addNewPost(username, postBody);
        return ResponseEntity.ok("Post added");
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
