package com.example.Twitter.Clone.Follower;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    @Autowired
    private UserService userService;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Follower> findFollowingsByUsername(String username) {
        return followerRepository.findFollowingsByUsername(username);
    }

    public void followUser(Principal principal, String username) {
        User user = userService.findByUserName(principal.getName());
        User followingUser = userService.findByUserName(username);

        Follower follower = new Follower();
        follower.setUser(user);
        follower.setFollowing(followingUser);
        followerRepository.save(follower);
    }

    public void unfollowUser (Long id) {
        boolean exists = followerRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("following with id " + id + " does not exists");
        }
        followerRepository.deleteById(id);
    }
}
