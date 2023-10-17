package com.example.Twitter.Clone.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping (path = "/posts")
    public List<Post> getPosts () {
        return postService.getPosts();
    }

    @GetMapping (path = "/posts/{postId}")
    public Optional<Post> getPostById (@PathVariable("postId") Long postId) {
        return postService.getPostById(postId);
    }

    @GetMapping (path = "/posts/user/{userId}")
    public List<Post> getAllPostsOfUser(@PathVariable("userId") Long id) {

        return postService.getPostsByUserId(id);
    }

    @GetMapping (path = "/posts/user/{userId}/followings")
    public List<Post> getAllPostsByFollowings (@PathVariable("userId") Long id) {

        return postService.getPostsByFollowings(id);
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

}
