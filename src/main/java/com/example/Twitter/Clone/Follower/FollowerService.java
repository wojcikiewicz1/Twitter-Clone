package com.example.Twitter.Clone.Follower;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    @Autowired
    FollowerRepository followerRepository;
    @Autowired
    UserRepository userRepository;

    public List<Follower> getFollowingsByUserId(Long id) {
        return followerRepository.findFollowingsByUserId(id);
    }

    public void followUser(String username, Long id) {
        Optional<User> user = userRepository.findUserByUsername(username);
        Optional<User> followingUser = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalStateException("there is no such user");
        }
        if (user.equals(followingUser)) {
            throw new IllegalStateException("can't follow yourself");
        }
        Follower follower = new Follower(user.get(), followingUser.get());
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
