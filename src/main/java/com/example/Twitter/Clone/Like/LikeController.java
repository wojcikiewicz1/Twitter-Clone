package com.example.Twitter.Clone.Like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class LikeController {

    @Autowired
    LikeService likeService;

    @PostMapping(path = "likes")
    public ResponseEntity likePost(@RequestHeader("username") String username, @RequestBody Long postId) {
        likeService.LikePost(username, postId);
        return ResponseEntity.ok("Post liked");
    }

    @DeleteMapping(path = "likes/{likeId}")
    public ResponseEntity unlikePost(@PathVariable("likeId") Long likeId) {
        likeService.unlikePost(likeId);
        return ResponseEntity.ok("Post unliked");
    }
}
