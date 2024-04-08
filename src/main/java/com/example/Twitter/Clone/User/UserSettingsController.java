package com.example.Twitter.Clone.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/settings")
public class UserSettingsController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/changePassword")
    public String changePasswordForm(Principal principal, String username, Model model) {
        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);
        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);

        model.addAttribute("passwordForm", new PasswordForm());
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(@ModelAttribute("passwordForm") @Validated PasswordForm passwordForm,
                                 BindingResult result, Principal principal, Model model) {
        User myUser = userService.findByUserName(principal.getName());
        model.addAttribute("myUser", myUser);

        if (!userService.verifyUserPassword(myUser, passwordForm.getCurrentPassword())) {
            result.rejectValue("currentPassword", null, "Invalid password.");
        }

        if (!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
            result.rejectValue("newPassword", null, "Passwords do not match.");
            result.rejectValue("confirmPassword", null, "Passwords do not match.");
        }

        if (!PasswordValidator.validate(passwordForm.getNewPassword())) {
            result.rejectValue("newPassword", null, "Password must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one number.");
        }

        if (result.hasErrors()) {
            model.addAttribute("passwordForm", passwordForm);
            return "changePassword";
        }

        userService.changePassword(myUser, passwordForm.getNewPassword());
        return "redirect:/logout";
    }

    @GetMapping("/updateUser")
    public String updateUser (Principal principal, Model model) {
        User myUser = userService.findByUserName(principal.getName());
        model.addAttribute("myUser", myUser);
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("myUser") User user, BindingResult result, Principal principal, Model model) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        User existingEmail = userRepository.findByEmail(user.getEmail());
        User myUser = userService.findByUserName(principal.getName());

        if (!myUser.getUsername().equals(user.getUsername()) && existingUser != null) {
            result.rejectValue("username", "user.username", "There is already an account registered with that username.");
        }

        if (!myUser.getEmail().equals(user.getEmail()) && existingEmail != null) {
            result.rejectValue("email", "user.email", "There is already an account registered with that email.");
        }

        if (result.hasErrors()) {
            model.addAttribute("myUser", user);
            return "updateUser";
        }

        myUser.setUsername(user.getUsername());
        myUser.setEmail(user.getEmail());
        myUser.setFirstName(user.getFirstName());
        myUser.setLastName(user.getLastName());

        userService.updateUser(myUser);
        return "redirect:/logout";
    }
}
