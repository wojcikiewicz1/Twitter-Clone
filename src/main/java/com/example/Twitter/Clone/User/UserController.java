package com.example.Twitter.Clone.User;

import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;


    @GetMapping("/{username}")
    public String profile(@PathVariable("username") String username, Model model, Principal principal) {
        String currentUsername = principal.getName();
        User myUser = userService.findByUserName(currentUsername);
        boolean isOwner = username.equals(currentUsername);
        model.addAttribute("isOwner", isOwner);

        List<Post> posts;
        if (isOwner) {
            posts = postService.getPostsByUsername(currentUsername);
        } else {
            User user = userService.findByUserName(username);
            if (user != null) {
                posts = postService.getPostsByUsername(username);
            } else {
                return "redirect:/error";
            }
        }

        for (Post post : posts) {
            int commentsCount = commentRepository.countByPostId(post.getId());
            post.setCommentsCount(commentsCount);
        }

        model.addAttribute("posts", posts);

        List<User> randomUsers = userService.findRandomUsers(currentUsername, 3);
        model.addAttribute("randomUsers", randomUsers);

        User user = userService.findByUserName(username);
        model.addAttribute("user", user);
        model.addAttribute("myUser", myUser);

        return "profile";
    }
}

/**
    @GetMapping("updateUser")
    public String updateUSer (Model model, Principal principal) {
        model.addAttribute("user", userService.findByUserName(principal.getName()));
        return "updateUser";
    }

    @PostMapping("updateUser")
    public String updateUser (@ModelAttribute("user") User user,Principal principal,
                              Model model, BindingResult result) {

        User myUser = userService.findByUserName(principal.getName());

        User existingUser = userRepository.findByUsername(user.getUsername());
        User existingUser1 = userRepository.findByEmail(user.getEmail());

        if (myUser.getUsername().equals(user.getUsername())){

        }
        else if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
            result.rejectValue("username", null, "There is already an account registered with the same username");
        }

        if (myUser.getEmail().equals(user.getEmail())){

        }
        else if(existingUser1 != null && existingUser1.getEmail() != null && !existingUser1.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "updateUser";
        }

        myUser.setUsername(user.getUsername());
        myUser.setEmail(user.getEmail());
        myUser.setFirstName(user.getFirstName());
        myUser.setLastName(user.getLastName());

        userService.updateUser(myUser);
        return "redirect:/logout";
    }

    @GetMapping("changePassword")
    public String changePassword (Model model, Principal principal) {
        model.addAttribute("passwordForm", new PasswordForm());
        return "changePassword";
    }

    @PostMapping("changePassword")
    public String changePassword (@ModelAttribute("passwordForm") @Validated PasswordForm passwordForm,
                                  BindingResult result, Principal principal, Model model) {

        User user = userService.findByUserName(principal.getName());

        if (!userService.verifyUserPassword(user, passwordForm.getCurrentPassword())) {
            result.rejectValue("currentPassword", null, "Invalid password.");
        }

        if (!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
            result.rejectValue("newPassword", null, "Passwords do not match.");
            result.rejectValue("confirmPassword", null, "Passwords do not match.");
        }

        if (result.hasErrors()) {
            model.addAttribute("passwordForm", passwordForm);
            return "changePassword";
        }

        userService.changePassword(user, passwordForm.getNewPassword());
        return "redirect:/logout";
    }

**/

