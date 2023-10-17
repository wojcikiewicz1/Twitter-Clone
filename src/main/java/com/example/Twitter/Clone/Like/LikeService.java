package com.example.Twitter.Clone.Like;

import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;

    public void LikePost (String username, Long postId) {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        Optional<Post> postById = postRepository.findById(postId);
        if(userByUsername.isEmpty()) {
            throw new IllegalStateException("there is no such user");
        } else if (postById.isEmpty()) {
            throw new IllegalStateException("there is no such post");
        }
        Like like = new Like(postById.get(), userByUsername.get());
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
