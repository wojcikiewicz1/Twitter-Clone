package com.example.Twitter.Clone.AuthController;

import com.example.Twitter.Clone.Follower.Follower;
import com.example.Twitter.Clone.Post.Post;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

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
    public String getAllPostsByFollowings (Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName());

        model.addAttribute("user", user);
        model.addAttribute("content", new String());

        postService.getPostsByFollowings(principal.getName());
        return "home";
    }

    @GetMapping ("/logout")
    public String logout() {
        return "logout";
    }
}
