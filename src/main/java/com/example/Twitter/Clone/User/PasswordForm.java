package com.example.Twitter.Clone.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PasswordForm {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
