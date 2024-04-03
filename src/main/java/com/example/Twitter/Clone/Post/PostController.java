package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Comment.CommentService;
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
public class PostController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{username}/{postId:[\\d]+}")
    public String getPostById(@PathVariable("username") String username, @PathVariable("postId") Long postId, Model model, Principal principal) {

        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);
        Post post = postService.getPostById(postId);
        List<Post> posts = postService.getPostsWithCommentsCount();
        List<Post> postsWithLikes = postService.getPostsWithLikesCount();
        List<Post> postsWithReposts = postService.getPostsWithRepostsCount();
        boolean isPostLiked = likeService.isPostLiked(principal, post.getId());
        boolean isFollowed = followerService.isFollowing(principal, username);
        boolean isPostReposted = postService.isPostRepostedByUser(principal, postId);

        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        model.addAttribute("posts", posts);
        model.addAttribute("postsWithLikes", postsWithLikes);
        model.addAttribute("postsWithReposts", postsWithReposts);
        model.addAttribute("isPostLiked", isPostLiked);
        model.addAttribute("isFollowed", isFollowed);
        model.addAttribute("isPostReposted", isPostReposted);

        List<Comment> comments = commentService.getCommentsByPostId(postId);
        for (Comment comment : comments) {
            int commentsCount = commentRepository.countByCommentId(comment.getId());
            comment.setCommentsCount(commentsCount);
        }
        model.addAttribute("comments", comments);

        Map<Long, Boolean> isLikedMap = new HashMap<>();
        Map<Long, Boolean> isRepostedMap = new HashMap<>();
        Map<Long, Boolean> isAuthorMap = new HashMap<>();
        for (Comment comment : comments) {
            boolean isLiked = likeService.isCommentLiked(principal, comment.getId());
            isLikedMap.put(comment.getId(), isLiked);
            boolean isReposted = commentService.isCommentRepostedByUser(principal, comment.getId());
            isRepostedMap.put(comment.getId(), isReposted);
            boolean isAuthor = comment.getUser().getUsername().equals(principal.getName());
            isAuthorMap.put(comment.getId(), isAuthor);
        }
        List<Comment> commentsWithLikes = commentService.getCommentsWithLikesCount();
        List<Comment> commentsWithReposts = commentService.getCommentsWithRepostsCount();

        model.addAttribute("isLikedMap", isLikedMap);
        model.addAttribute("isRepostedMap", isRepostedMap);
        model.addAttribute("isAuthorMap", isAuthorMap);
        model.addAttribute("commentsWithLikes", commentsWithLikes);
        model.addAttribute("commentsWithReposts", commentsWithReposts);

        UserController.isUserFollowed(model, principal, userRepository, followerService);

        String principalUsername = principal.getName();
        boolean isOwner = username.equals(principalUsername);
        model.addAttribute("isOwner", isOwner);

        return "post";
    }

    @PostMapping("/addPost")
    public String addPostMain(@ModelAttribute("content") String content, Principal principal) {
        postService.addNewPost(principal, content);
        return "redirect:/home";
    }

    @PostMapping("/api/addPost")
    @ResponseBody
    public ResponseEntity<?> addPost(@ModelAttribute("content") String content, Principal principal) {
        try {
            postService.addNewPost(principal, content);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error message");
        }
    }

    @PostMapping("/deletePost")
    public ResponseEntity<?> deletePost(@RequestParam("id") Long postId) {
        try {
            Post post = postService.getPostById(postId);
            postService.deletePost(post);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting post");
        }
    }

    @PostMapping("/api/pinPost/{id}")
    public ResponseEntity<?> pinPost(@PathVariable Long id) {
        postService.pinPost(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/unpinPost/{id}")
    public ResponseEntity<?> unpinPost(@PathVariable Long id) {
        postService.unpinPost(id);
        return ResponseEntity.ok().build();
    }

}