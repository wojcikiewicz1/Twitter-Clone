package com.example.Twitter.Clone.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName());

        model.addAttribute("user", user);

        return "profile";
    }

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


}
