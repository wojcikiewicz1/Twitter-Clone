package com.example.Twitter.Clone.Like;

import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/api/like/post")
    public ResponseEntity<?> likePost(Principal principal, @RequestParam("postId") Long postId) {
        try {
            likeService.likePost(principal, postId);
            int likesCount = postRepository.countLikesByPostId(postId);
            return ResponseEntity.ok(Map.of("likesCount", likesCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to like post");
        }
    }

    @PostMapping("/api/like/comment")
    public ResponseEntity<?> likeComment(Principal principal, @RequestParam("commentId") Long commentId) {
        try {
            likeService.likeComment(principal, commentId);
            int likesCount = commentRepository.countLikesByCommentId(commentId);
            return ResponseEntity.ok(Map.of("likesCount", likesCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to like comment");
        }
    }

    @PostMapping("/api/unlike/post")
    public ResponseEntity<?> unlikePost (Principal principal, @RequestParam("postId") Long postId) {
        try {
            likeService.unlikePost(principal, postId);
            int likesCount = postRepository.countLikesByPostId(postId);
            return ResponseEntity.ok(Map.of("likesCount", likesCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unlike post: " + postId);
        }
    }

    @PostMapping("/api/unlike/comment")
    public ResponseEntity<?> unlikeComment (Principal principal, @RequestParam("commentId") Long commentId) {
        try {
            likeService.unlikeComment(principal, commentId);
            int likesCount = commentRepository.countLikesByCommentId(commentId);
            return ResponseEntity.ok(Map.of("likesCount", likesCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unlike comment: " + commentId);
        }
    }

}
