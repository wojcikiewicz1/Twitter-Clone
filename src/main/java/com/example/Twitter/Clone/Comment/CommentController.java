package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Follower.FollowerService;
import com.example.Twitter.Clone.Like.LikeService;
import com.example.Twitter.Clone.Post.Post;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FollowerService followerService;

    @GetMapping("/{username}/comment/{commentId:[\\d]+}")
    public String getCommentById(@PathVariable("username") String username, @PathVariable("commentId") Long commentId, Model model, Principal principal) {

        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);
        Comment comment = commentService.getCommentById(commentId);
        List<Comment> comments = commentService.getCommentsWithCommentsCount();
        List<Comment> responses = commentService.getResponsesByCommentId(commentId);
        List<Comment> commentsWithLikes = commentService.getCommentsWithLikesCount();
        boolean isCommentLiked = likeService.isCommentLiked(principal, comment.getId());
        boolean isFollowed = followerService.isFollowing(principal, username);

        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);
        model.addAttribute("comment", comment);
        model.addAttribute("comments", comments);
        model.addAttribute("responses", responses);
        model.addAttribute("commentsWithLikes", commentsWithLikes);
        model.addAttribute("isCommentLiked", isCommentLiked);
        model.addAttribute("isFollowed", isFollowed);

        for (Comment response : responses) {
            int commentsCount = commentRepository.countByCommentId(response.getId());
            int likesCount = commentRepository.countLikesByCommentId(response.getId());
            response.setCommentsCount(commentsCount);
            response.setLikesCount(likesCount);
        }

        likedResponses(model, principal, commentId, commentService, likeService);

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

    public static void likedResponses(Model model, Principal principal, Long commentId, CommentService commentService , LikeService likeService) {
        List<Comment> responses = commentService.getResponsesByCommentId(commentId);
        Map<Long, Boolean> isLikedMap = new HashMap<>();
        for (Comment response : responses) {
            boolean isLiked = likeService.isCommentLiked(principal, response.getId());
            isLikedMap.put(response.getId(), isLiked);
        }
        model.addAttribute("responses", responses);
        model.addAttribute("isLikedMap", isLikedMap);
    }

    /**

    @DeleteMapping(path = "comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.DeleteComment(commentId);
        return ResponseEntity.ok("Comment deleted");
    }

    **/
}
