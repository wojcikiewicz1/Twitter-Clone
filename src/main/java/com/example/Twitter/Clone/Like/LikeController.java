package com.example.Twitter.Clone.Like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/api/like/post")
    public ResponseEntity<?> likePost(Principal principal, @RequestParam("postId") Long postId) {
        try {
            likeService.likePost(principal, postId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to like post");
        }
    }

    @PostMapping("/api/like/comment")
    public ResponseEntity<?> likeComment(Principal principal, @RequestParam("commentId") Long commentId) {
        try {
            likeService.likeComment(principal, commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to like comment");
        }
    }

    @DeleteMapping("/api/unlike/post")
    public ResponseEntity<?> unlikePost (Principal principal, @RequestParam("postId") Long postId) {
        try {
            likeService.unlikePost(principal, postId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unlike post: " + postId);
        }
    }

    @DeleteMapping("/api/unlike/comment")
    public ResponseEntity<?> unlikeComment (Principal principal, @RequestParam("commentId") Long commentId) {
        try {
            likeService.unlikeComment(principal, commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unlike comment: " + commentId);
        }
    }

}
