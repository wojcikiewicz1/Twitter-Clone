package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Follower.FollowerService;
import com.example.Twitter.Clone.Like.LikeService;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserController;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private UserRepository userRepository;

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
        Map<Long, Boolean> isAuthorMap = new HashMap<>();
        for (Comment response : responses) {
            boolean isLiked = likeService.isCommentLiked(principal, response.getId());
            isLikedMap.put(response.getId(), isLiked);
            boolean isReposted = commentService.isCommentRepostedByUser(principal, response.getId());
            isRepostedMap.put(response.getId(), isReposted);
            boolean isAuthor = response.getUser().getUsername().equals(principal.getName());
            isAuthorMap.put(response.getId(), isAuthor);
        }
        List<Comment> responsesWithLikes = commentService.getCommentsWithLikesCount();
        List<Comment> responsesWithReposts = commentService.getCommentsWithRepostsCount();

        model.addAttribute("isLikedMap", isLikedMap);
        model.addAttribute("isRepostedMap", isRepostedMap);
        model.addAttribute("isAuthorMap", isAuthorMap);
        model.addAttribute("responsesWithLikes", responsesWithLikes);
        model.addAttribute("responsesWithReposts", responsesWithReposts);

        UserController.isUserFollowed(model, principal, userRepository, followerService);

        String principalUsername = principal.getName();
        boolean isOwner = username.equals(principalUsername);
        model.addAttribute("isOwner", isOwner);

        return "comment";
    }
    @PostMapping ("/{username}/{postId:[\\d]+}")
    public String addCommentToPost(@PathVariable("username") String username, @PathVariable("postId") Long postId, @RequestParam(name = "gifUrl", required = false) String gifUrl, @ModelAttribute("body") String body, Principal principal) {
        commentService.AddCommentToPost(principal, postId, body, gifUrl);
        return "redirect:/" + username + "/" + postId;
    }

    @PostMapping ("/{username}/comment/{commentId:[\\d]+}")
    public String addCommentToComment(@PathVariable("username") String username, @PathVariable("commentId") Long commentId, @RequestParam(name = "gifUrl", required = false) String gifUrl, @ModelAttribute("body") String body, Principal principal) {
        commentService.AddCommentToComment(principal, commentId, body, gifUrl);
        return "redirect:/" + username + "/comment/" + commentId;
    }

    @PostMapping("/deleteComment")
    public ResponseEntity<?> deleteComment(@RequestParam("commentId") Long commentId) {
        try {
            Comment comment = commentService.getCommentById(commentId);
            commentService.deleteComment(comment);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment");
        }
    }

    @PostMapping("/api/pinComment/{id}")
    public ResponseEntity<?> pinComment(@PathVariable Long id) {
        commentService.pinComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/unpinComment/{id}")
    public ResponseEntity<?> unpinComment(@PathVariable Long id) {
        commentService.unpinComment(id);
        return ResponseEntity.ok().build();
    }

}
