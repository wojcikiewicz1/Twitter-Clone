package com.example.Twitter.Clone.Follower;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    @Autowired
    private UserService userService;
    @Autowired
    FollowerRepository followerRepository;
    @Autowired
    UserRepository userRepository;

    public List<Follower> getFollowingsByUserId(Long id) {
        return followerRepository.findFollowingsByUserId(id);
    }

    public void followUser(String username, Long id) {
        User user = userService.findByUserName(username);
        Optional<User> followingUser = userRepository.findById(id);

        Follower follower = new Follower(user, followingUser.get());
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
