package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Comment.CommentService;
import com.example.Twitter.Clone.Follower.FollowerService;
import com.example.Twitter.Clone.Like.LikeService;
import com.example.Twitter.Clone.User.User;
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
    private PostRepository postRepository;


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
            int likesCount = commentRepository.countLikesByCommentId(comment.getId());
            comment.setCommentsCount(commentsCount);
            comment.setLikesCount(likesCount);
        }
        model.addAttribute("comments", comments);

        likedComments(model, principal, postId, commentService, likeService);

        return "post";
    }

    @PostMapping("/api/addPost")
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
        try {
        User user = userService.findByUserName(principal.getName());
        postService.repost(postId, user);
        int repostsCount = postRepository.countRepostsByPostId(postId);
        return ResponseEntity.ok(Map.of("repostsCount", repostsCount, "isReposted", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to repost post: " + postId);
        }
    }

    @PostMapping("/api/unrepost")
    public ResponseEntity<?> unrepost(@RequestParam("postId") Long postId, Principal principal) {
        try {
            postService.unRepost(postId, principal);
            int repostsCount = postRepository.countRepostsByPostId(postId);
            return ResponseEntity.ok(Map.of("repostsCount", repostsCount, "isReposted", false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unrepost post: " + postId);
        }
    }

/**
 @DeleteMapping (path = " / posts / { postId } ")
 public ResponseEntity deletePost (@PathVariable("postId") Long postId) {
 postService.deletePost(postId);
 return ResponseEntity.ok("Post deleted");
 }
 **/

}