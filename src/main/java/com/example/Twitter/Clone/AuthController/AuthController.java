package com.example.Twitter.Clone.AuthController;

import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Follower.FollowerService;
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
import java.util.List;

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
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
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
        model.addAttribute("user", user);
        model.addAttribute("myUser", myUser);
        model.addAttribute("content", "");

        List<User> randomUsers = userService.findRandomUsers(principalUsername, 3);
        model.addAttribute("randomUsers", randomUsers);

        List<Post> posts = postService.getPostsByFollowings(principalUsername);
        for (Post post : posts) {
            int commentsCount = postRepository.countByPostId(post.getId());
            post.setCommentsCount(commentsCount);
        }
        model.addAttribute("posts", posts);
        return "home";
    }

    @GetMapping ("/logout")
    public String logout() {
        return "logout";
    }
}
