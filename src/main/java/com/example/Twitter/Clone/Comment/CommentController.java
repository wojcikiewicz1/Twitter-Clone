package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @GetMapping("/{username}/comment/{commentId:[\\d]+}")
    public String getCommentById(@PathVariable("username") String username, @PathVariable("commentId") Long commentId, Model model, Principal principal) {

        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);
        Comment comment = commentService.getCommentById(commentId);
        List<Comment> commentList = commentService.getResponsesByCommentId(commentId);
        List<Comment> comments = commentService.getCommentsWithCommentsCount();
        List<Comment> commentsWithLikes = commentService.getCommentsWithLikesCount();

        model.addAttribute("user", user);
        model.addAttribute("comment", comment);
        model.addAttribute("comments", comments);
        model.addAttribute("myUser", myUser);
        model.addAttribute("commentList", commentList);
        model.addAttribute("commentsWithLikes", commentsWithLikes);

        return "comment";
    }
    @PostMapping ("/{username}/{postId:[\\d]+}")
    public String addCommentToPost(@PathVariable("username") String username, @PathVariable("postId") Long postId, @ModelAttribute("body") String body, Principal principal) {
        commentService.AddCommentToPost(principal, postId, body);
        return "redirect:/" + username + "/" + postId;
    }

    @PostMapping ("/{username}/comment/{commentId:[\\d]+}")
    public String addCommentToComment(@PathVariable("username") String username, @PathVariable("commentId") Long commentId, @ModelAttribute("body") String body, Principal principal) {
        commentService.AddCommentToComment(principal, commentId, body);
        return "redirect:/" + username + "/comment/" + commentId;
    }

    /**

    @DeleteMapping(path = "comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.DeleteComment(commentId);
        return ResponseEntity.ok("Comment deleted");
    }

    **/
}
