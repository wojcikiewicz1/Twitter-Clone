package com.example.Twitter.Clone.Follower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @PostMapping("/api/follow")
    public ResponseEntity<?> followUser(Principal principal, @RequestParam("username") String username) {
        try {
            followerService.followUser(principal, username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to follow user: " + username);
        }
    }

    @PostMapping("/api/unfollow")
    public ResponseEntity<?> unfollowUser (Principal principal, @RequestParam("username") String username) {
        try {
            followerService.unfollowUser(principal, username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to unfollow user: " + username);
        }
    }

}
