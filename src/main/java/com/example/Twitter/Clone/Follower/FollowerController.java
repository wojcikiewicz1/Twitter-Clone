package com.example.Twitter.Clone.Follower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @PostMapping ("/home/followUser")
    public String followUser(Principal principal, @ModelAttribute("username") String username) {
        followerService.followUser(principal, username);
        return "redirect:/home";
    }

    @DeleteMapping ("/home/followUser")
    public String unfollowUser (@PathVariable("followId")Long id) {
        followerService.unfollowUser(id);
        return "redirect:/home";
    }

}
