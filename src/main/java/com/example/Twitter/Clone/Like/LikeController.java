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
    public ResponseEntity<?> likePost(@RequestParam("postId") Long postId, Principal principal) {
        try {
            likeService.likePost(principal, postId);
            int likesCount = postRepository.countLikesByPostId(postId);
            return ResponseEntity.ok(Map.of("likesCount", likesCount, "isLiked", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to like post");
        }
    }

    @PostMapping("/api/like/comment")
    public ResponseEntity<?> likeComment(@RequestParam("commentId") Long commentId, Principal principal) {
        try {
            likeService.likeComment(principal, commentId);
            int likesCount = commentRepository.countLikesByCommentId(commentId);
            return ResponseEntity.ok(Map.of("likesCount", likesCount, "isLiked", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to like comment");
        }
    }

    @PostMapping("/api/unlike/post")
    public ResponseEntity<?> unlikePost(@RequestParam("postId") Long postId ,Principal principal) {
        try {
            likeService.unlikePost(principal, postId);
            int likesCount = postRepository.countLikesByPostId(postId);
            return ResponseEntity.ok(Map.of("likesCount", likesCount, "isLiked", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unlike post: " + postId);
        }
    }

    @PostMapping("/api/unlike/comment")
    public ResponseEntity<?> unlikeComment (@RequestParam("commentId") Long commentId, Principal principal) {
        try {
            likeService.unlikeComment(principal, commentId);
            int likesCount = commentRepository.countLikesByCommentId(commentId);
            return ResponseEntity.ok(Map.of("likesCount", likesCount, "isLiked", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unlike comment: " + commentId);
        }
    }

}
