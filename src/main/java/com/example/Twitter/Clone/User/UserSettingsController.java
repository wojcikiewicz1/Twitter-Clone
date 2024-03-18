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


    @GetMapping("/changePassword")
    public String changePasswordForm(Principal principal, String username, Model model) {
        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);
        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);

        model.addAttribute("passwordForm", new PasswordForm());
        return "changePassword";
    }

    @PostMapping("changePassword")
    public String changePassword (@ModelAttribute("passwordForm") @Validated PasswordForm passwordForm,
                                  BindingResult result, Principal principal, Model model, String username) {
        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);
        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);

        if (!userService.verifyUserPassword(myUser, passwordForm.getCurrentPassword())) {
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

        userService.changePassword(myUser, passwordForm.getNewPassword());
        return "redirect:/logout";
    }

}
