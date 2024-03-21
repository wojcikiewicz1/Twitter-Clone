package com.example.Twitter.Clone.Repost;

import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

@Controller
public class RepostController {

    @Autowired
    private UserService userService;
    @Autowired
    private RepostService repostService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/api/repost/post")
    public ResponseEntity<?> repostPost(@RequestParam("postId") Long postId, Principal principal) {
        try {
            User user = userService.findByUserName(principal.getName());
            repostService.repostPost(postId, user);
            int repostsCount = postRepository.countRepostsByPostId(postId);
            return ResponseEntity.ok(Map.of("repostsCount", repostsCount, "isReposted", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to repost post: " + postId);
        }
    }

    @PostMapping("/api/unrepost/post")
    public ResponseEntity<?> unrepostPost(@RequestParam("postId") Long postId, Principal principal) {
        try {
            repostService.unRepostPost(postId, principal);
            int repostsCount = postRepository.countRepostsByPostId(postId);
            return ResponseEntity.ok(Map.of("repostsCount", repostsCount, "isReposted", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unrepost post: " + postId);
        }
    }

    @PostMapping("/api/repost/comment")
    public ResponseEntity<?> repostComment(@RequestParam("commentId") Long commentId, Principal principal) {
        try {
            User user = userService.findByUserName(principal.getName());
            repostService.repostComment(commentId, user);
            int repostsCount = commentRepository.countRepostsByCommentId(commentId);
            return ResponseEntity.ok(Map.of("repostsCount", repostsCount, "isReposted", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to repost comment: " + commentId);
        }
    }

    @PostMapping("/api/unrepost/comment")
    public ResponseEntity<?> unrepostComment(@RequestParam("commentId") Long commentId, Principal principal) {
        try {
            repostService.unRepostComment(commentId, principal);
            int repostsCount = commentRepository.countRepostsByCommentId(commentId);
            return ResponseEntity.ok(Map.of("repostsCount", repostsCount, "isReposted", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unrepost comment: " + commentId);
        }
    }
}
