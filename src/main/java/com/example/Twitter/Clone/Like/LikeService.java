package com.example.Twitter.Clone.Like;

import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private UserService userService;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    public void LikePost (String username, Long postId) {
        User userByUsername = userService.findByUserName(username);
        Optional<Post> postById = postRepository.findById(postId);

        Like like = new Like(postById.get(), userByUsername);
        likeRepository.save(like);
    }

    public void unlikePost (Long likeId) {
        boolean exists = likeRepository.existsById(likeId);
        if (!exists) {
            throw new IllegalStateException("like with id " + likeId + " does not exists");
        }
        likeRepository.deleteById(likeId);
    }

}
