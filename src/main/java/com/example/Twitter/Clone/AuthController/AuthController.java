package com.example.Twitter.Clone.AuthController;

import com.example.Twitter.Clone.Follower.FollowerService;
import com.example.Twitter.Clone.Like.LikeService;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.Post.PostService;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeService likeService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() { return "login";
    }

    @GetMapping ("/register")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user")User user, Model model, BindingResult result) {

        User existingUser = userRepository.findByUsername(user.getUsername());
        User existingUser1 = userRepository.findByEmail(user.getEmail());

        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
            result.rejectValue("username", null, "There is already an account registered with the same username");
        }

        if(existingUser1 != null && existingUser1.getEmail() != null && !existingUser1.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "/register";
        }

        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping ("/home")
    public String getAllPostsByFollowings (Model model, Principal principal, String username) {
        String principalUsername = principal.getName();
        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);
        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);
        model.addAttribute("content", "");

        List<Post> posts = postService.getPostsAndRepostsByFollowings(principalUsername);
        for (Post post : posts) {
            int commentsCount = postRepository.countByPostId(post.getId());
            int likesCount = postRepository.countLikesByPostId(post.getId());
            int repostsCount = postRepository.countRepostsByPostId(post.getId());
            post.setCommentsCount(commentsCount);
            post.setLikesCount(likesCount);
            post.setRepostsCount(repostsCount);
        }
        model.addAttribute("posts", posts);

        preparePostsData(model, principal, principalUsername, postService, likeService);
        randomUsers(model, principal, username, principalUsername, userService, followerService);

        return "home";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(Principal principal) {
        User user = userService.findByUserName(principal.getName());
        userService.deleteUser(user);
        return "redirect:/logout";
    }

    public static void randomUsers(Model model, Principal principal, String username, String principalUsername, UserService userService, FollowerService followerService) {
        List<User> randomUsers = userService.findRandomUsers(principalUsername,username, 3);
        Map<String, Boolean> isFollowingMap = new HashMap<>();
        for (User randomUser : randomUsers) {
            boolean isFollowing = followerService.isFollowing(principal, randomUser.getUsername());
            isFollowingMap.put(randomUser.getUsername(), isFollowing);
        }
        model.addAttribute("randomUsers", randomUsers);
        model.addAttribute("isFollowingMap", isFollowingMap);
    }

    public static void preparePostsData(Model model, Principal principal, String principalUsername, PostService postService, LikeService likeService) {
        List<Post> posts = postService.getPostsAndRepostsByFollowings(principalUsername);
        Map<Long, Boolean> isLikedMap = new HashMap<>();
        Map<Long, Boolean> isRepostedMap = new HashMap<>();

        for (Post post : posts) {
            boolean isLiked = likeService.isPostLiked(principal, post.getId());
            boolean isReposted = postService.isPostRepostedByUser(principal, post.getId());

            isLikedMap.put(post.getId(), isLiked);
            isRepostedMap.put(post.getId(), isReposted);
        }

        model.addAttribute("posts", posts);
        model.addAttribute("isLikedMap", isLikedMap);
        model.addAttribute("isRepostedMap", isRepostedMap);
    }

}
