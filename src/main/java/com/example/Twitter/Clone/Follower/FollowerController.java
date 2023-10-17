package com.example.Twitter.Clone.Follower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class FollowerController {

    @Autowired
    FollowerService followerService;

    @GetMapping(path = "/user/{userId}/followings")
    public List<Follower> getAllFollowingsOfUser(@PathVariable("userId") Long id) {
        return followerService.getFollowingsByUserId(id);
    }

    @PostMapping(path = "followers")
    public ResponseEntity followUser(@RequestHeader("username") String username, @RequestBody Long id) {
        followerService.followUser(username, id);
        return ResponseEntity.ok("user followed");
    }

    @DeleteMapping(path = "followers/{followId}")
    public ResponseEntity unfollowUser (@PathVariable("followId")Long id) {
        followerService.unfollowUser(id);
        return ResponseEntity.ok("Following deleted");
    }

}
