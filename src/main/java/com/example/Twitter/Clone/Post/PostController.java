package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Comment.CommentService;
import com.example.Twitter.Clone.Follower.FollowerService;
import com.example.Twitter.Clone.Like.LikeService;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/{username}/{postId:[\\d]+}")
    public String getPostById(@PathVariable("username") String username, @PathVariable("postId") Long postId, Model model, Principal principal) {

        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);
        Post post = postService.getPostById(postId);
        List<Post> posts = postService.getPostsWithCommentsCount();
        List<Post> postsWithLikes = postService.getPostsWithLikesCount();
        boolean isPostLiked = likeService.isPostLiked(principal, post.getId());
        boolean isFollowed = followerService.isFollowing(principal, username);

        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        model.addAttribute("posts", posts);
        model.addAttribute("postsWithLikes", postsWithLikes);
        model.addAttribute("isPostLiked", isPostLiked);
        model.addAttribute("isFollowed", isFollowed);

        List<Comment> comments = commentService.getCommentsByPostId(postId);
        for (Comment comment : comments) {
            int commentsCount = commentRepository.countByCommentId(comment.getId());
            int likesCount = commentRepository.countLikesByCommentId(comment.getId());
            comment.setCommentsCount(commentsCount);
            comment.setLikesCount(likesCount);
        }
        model.addAttribute("comments", comments);

        likedComments(model, principal, postId, commentService, likeService);

        return "post";
    }

    @PostMapping("/home/addPost")
    public String addPost(@ModelAttribute("content") String content, Principal principal) {
        postService.addNewPost(principal, content);
        return "redirect:/home";
    }

    public static void likedComments(Model model, Principal principal, Long postId, CommentService commentService , LikeService likeService) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        Map<Long, Boolean> isLikedMap = new HashMap<>();
        for (Comment comment : comments) {
            boolean isLiked = likeService.isCommentLiked(principal, comment.getId());
            isLikedMap.put(comment.getId(), isLiked);
        }
        model.addAttribute("comments", comments);
        model.addAttribute("isLikedMap", isLikedMap);
    }

    @PostMapping("/api/repost")
    public ResponseEntity<?> repost(@RequestParam("postId") Long postId, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        postService.repost(postId, user);
        return ResponseEntity.ok().body("Post reposted successfully");
    }

/**
 @DeleteMapping (path = " / posts / { postId } ")
 public ResponseEntity deletePost (@PathVariable("postId") Long postId) {
 postService.deletePost(postId);
 return ResponseEntity.ok("Post deleted");
 }
 **/

}