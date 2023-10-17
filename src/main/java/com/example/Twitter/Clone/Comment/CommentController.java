package com.example.Twitter.Clone.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping(path = "/posts/{postId}/comments")
    public List<Comment> getPostComments (@PathVariable("postId") Long id) {
        return commentService.getCommentsByPostId(id);
    }
    @PostMapping (path = "comments")
    public ResponseEntity addComment(@RequestHeader("username") String username, @RequestHeader("post_id")Long postId, @RequestBody String commentBody) {
        commentService.AddCommentToPost(username, postId, commentBody);
        return ResponseEntity.ok("Comment added");
    }

    @DeleteMapping(path = "comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.DeleteComment(commentId);
        return ResponseEntity.ok("Comment deleted");
    }
}
