package com.example.Twitter.Clone.Follower;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class FollowerService {

    @Autowired
    private UserService userService;

    @Autowired
    private FollowerRepository followerRepository;

    public List<Follower> findFollowingsByUsername(String username) {
        return followerRepository.findFollowingsByUsername(username);
    }

    public void followUser(Principal principal, String username) {
        User myUser = userService.findByUserName(principal.getName());
        User userToFollow = userService.findByUserName(username);

        Follower follower = new Follower();
        follower.setUser(myUser);
        follower.setUserToFollow(userToFollow);
        followerRepository.save(follower);
    }

    public void unfollowUser (Principal principal, String username) {
        User myUser = userService.findByUserName(principal.getName());
        User userToUnfollow = userService.findByUserName(username);

        Follower follower = followerRepository.findByUserAndUserToFollow(myUser, userToUnfollow);

        followerRepository.delete(follower);
    }

    public boolean isFollowing(Principal principal, String username) {
        User myUser = userService.findByUserName(principal.getName());
        User userToCheck = userService.findByUserName(username);

        Follower follower = followerRepository.findByUserAndUserToFollow(myUser, userToCheck);
        return follower != null;
    }
}
