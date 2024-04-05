package com.example.Twitter.Clone.Follower;

import com.example.Twitter.Clone.AuthController.AuthController;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowerController {

    @Autowired
    private FollowerService followerService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/{username}/following")
    public String getFollowings (Principal principal, @PathVariable("username") String username, Model model){
        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);

        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);

        List<Follower> followings = followerService.findFollowingsByUsername(username);
        Map<String, Boolean> isFollowingMap = new HashMap<>();
        for (Follower following : followings) {
            boolean isFollowing = followerService.isFollowing(principal, following.getUserToFollow().getUsername());
            isFollowingMap.put(following.getUserToFollow().getUsername(), isFollowing);
        }

        model.addAttribute("isFollowingMap", isFollowingMap);
        model.addAttribute("followings", followings);

        AuthController.randomUsers(model, principal, username, principal.getName(), userService, followerService);
        return "following";
    }

    @GetMapping("/{username}/followers")
    public String getFollowers (Principal principal,  @PathVariable("username") String username, Model model){
        User myUser = userService.findByUserName(principal.getName());
        User user = userService.findByUserName(username);

        model.addAttribute("myUser", myUser);
        model.addAttribute("user", user);

        List<Follower> followers = followerService.findFollowsByUsername(username);
        Map<String, Boolean> isFollowingMap = new HashMap<>();
        for (Follower follower : followers) {
            boolean isFollowing = followerService.isFollowing(principal, follower.getUser().getUsername());
            isFollowingMap.put(follower.getUser().getUsername(), isFollowing);
        }
        model.addAttribute("isFollowingMap", isFollowingMap);
        model.addAttribute("followers", followers);

        AuthController.randomUsers(model, principal, username, principal.getName(), userService, followerService);
        return "followers";
    }

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

    @GetMapping("/whoToFollow")
    public String whoToFollow (Principal principal, Model model){
        User myUser = userService.findByUserName(principal.getName());
        model.addAttribute("myUser", myUser);

        List<User> users = userRepository.findAll();
        Map<String, Boolean> isFollowingMap = new HashMap<>();
        List<User> notFollowingUsers = new ArrayList<>();

        for (User user : users) {
            boolean isFollowing = followerService.isFollowing(principal, user.getUsername());
            isFollowingMap.put(user.getUsername(), isFollowing);
            if (!isFollowing && !user.getUsername().equals(principal.getName())) {
                notFollowingUsers.add(user);
            }
        }

        model.addAttribute("isFollowingMap", isFollowingMap);
        model.addAttribute("notFollowingUsers", notFollowingUsers);

        return "whoToFollow";
    }
}
