package com.example.Twitter.Clone.User;

import com.example.Twitter.Clone.AuthController.AuthController;
import com.example.Twitter.Clone.Follower.FollowerRepository;
import com.example.Twitter.Clone.Follower.FollowerService;
import com.example.Twitter.Clone.Like.LikeService;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private PostRepository postRepository;


    @GetMapping("/{username}")
    public String profile(@PathVariable("username") String username, Model model, Principal principal) {
        String principalUsername = principal.getName();
        User myUser = userService.findByUserName(principalUsername);
        boolean isOwner = username.equals(principalUsername);
        model.addAttribute("isOwner", isOwner);

        List<Post> posts;
        if (isOwner) {
            posts = postService.getAllUserPostsAndReposts(principalUsername);
        } else {
            User user = userService.findByUserName(username);
            if (user != null) {
                posts = postService.getAllUserPostsAndReposts(username);
            } else {
                return "redirect:/error";
            }
        }

        for (Post post : posts) {
            int commentsCount = postRepository.countByPostId(post.getId());
            post.setCommentsCount(commentsCount);
        }
        model.addAttribute("posts", posts);

        Map<Long, Boolean> isLikedMap = new HashMap<>();
        for (Post post : posts) {
            boolean isLiked = likeService.isPostLiked(principal, post.getId());
            isLikedMap.put(post.getId(), isLiked);
        }
        List<Post> postsWithLikes = postService.getPostsWithLikesCount();
        model.addAttribute("isLikedMap", isLikedMap);
        model.addAttribute("postsWithLikes", postsWithLikes);

        User user = userService.findByUserName(username);
        model.addAttribute("user", user);
        model.addAttribute("myUser", myUser);

        boolean isFollowingMain = followerService.isFollowing(principal, username);
        model.addAttribute("isFollowingMain", isFollowingMain);

        int followers = followerRepository.followers(user.getId());
        int following = followerRepository.following(user.getId());
        model.addAttribute("following", following);
        model.addAttribute("followers", followers);

        int numberOfPosts = postRepository.numberOfPosts(user.getId());
        model.addAttribute("numberOfPosts", numberOfPosts);

        AuthController.randomUsers(model, principal, username, principalUsername, userService, followerService);

        return "profile";
    }

}