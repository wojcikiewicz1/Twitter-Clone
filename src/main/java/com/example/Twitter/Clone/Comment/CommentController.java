package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Follower.FollowerService;
import com.example.Twitter.Clone.Like.LikeService;
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
        List<Comment> commentsWithLikes = commentService.getCommentsWithLikesCount();
        List<Comment> commentsWithReposts = commentService.getCommentsWithRepostsCount();
        boolean isCommentLiked = likeService.isCommentLiked(principal, comment.getId());
        boolean isFollowed = followerService.isFollowing(principal, username);
        boolean isCommentReposted = commentService.isCommentRepostedByUser(principal, commentId);

        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);
        model.addAttribute("comment", comment);
        model.addAttribute("comments", comments);
        model.addAttribute("commentsWithLikes", commentsWithLikes);
        model.addAttribute("commentsWithReposts", commentsWithReposts);
        model.addAttribute("isCommentLiked", isCommentLiked);
        model.addAttribute("isFollowed", isFollowed);
        model.addAttribute("isCommentReposted", isCommentReposted);

        List<Comment> responses = commentService.getResponsesByCommentId(commentId);
        for (Comment response : responses) {
            int commentsCount = commentRepository.countByCommentId(response.getId());
            response.setCommentsCount(commentsCount);
        }
        model.addAttribute("responses", responses);


        Map<Long, Boolean> isLikedMap = new HashMap<>();
        Map<Long, Boolean> isRepostedMap = new HashMap<>();
        for (Comment response : responses) {
            boolean isLiked = likeService.isCommentLiked(principal, response.getId());
            isLikedMap.put(response.getId(), isLiked);
            boolean isReposted = commentService.isCommentRepostedByUser(principal, response.getId());
            isRepostedMap.put(response.getId(), isReposted);
        }
        List<Comment> responsesWithLikes = commentService.getCommentsWithLikesCount();
        List<Comment> responsesWithReposts = commentService.getCommentsWithRepostsCount();

        model.addAttribute("isLikedMap", isLikedMap);
        model.addAttribute("isRepostedMap", isRepostedMap);
        model.addAttribute("responsesWithLikes", responsesWithLikes);
        model.addAttribute("responsesWithReposts", responsesWithReposts);

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
