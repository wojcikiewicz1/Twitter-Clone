package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class PostService {

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("Post with id " + postId + " does not exist"));
    }

    public List<Post> getPostsByUsername(String username) {

        return postRepository.findPostsByUsername(username);
    }

    public List<Post> getPostsByFollowings (String username) {

        return postRepository.findPostsByFollowings(username);
    }

    public void addNewPost(Principal principal, String body) {
        User user = userRepository.findByUsername(principal.getName());
        Post post = new Post(user, body);
        postRepository.save(post);
    }

    public void sharePost(Principal principal, Long postId) {
        User user = userService.findByUserName(principal.getName());
        Optional<Post> optionalPost = postRepository.findById(postId);

        Post post = new Post(user, Objects.toString((postRepository.findById(postId)).get()));
        postRepository.save(post);
    }

    public void deletePost(Long postId) {

        postRepository.deleteById(postId);
    }
}

