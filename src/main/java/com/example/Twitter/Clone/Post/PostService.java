package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;


    public List<Post> getPosts() {

        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long postId) {
        boolean exists = postRepository.existsById(postId);
        if (!exists) {
            throw new IllegalStateException("post with id " + postId + " does not exists");
        }
        return postRepository.findById(postId);
    }

    public List<Post> getPostsByUserId(Long id) {

        return postRepository.findPostsByUserId(id);
    }

    public List<Post> getPostsByFollowings (Long id) {
        return postRepository.findPostsByFollowings(id);
    }


    public void addNewPost(String username, String postBody) {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        if (userByUsername.isEmpty()) {
            throw new IllegalStateException("there is no such user");
        }
        Post post = new Post(userByUsername.get(), postBody);
        postRepository.save(post);
    }

    public void sharePost(String username, Long postId) {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        Optional<Post> postById = postRepository.findById(postId);
        if(userByUsername.isEmpty()) {
            throw new IllegalStateException("there is no such user");
        } else if (postById.isEmpty()) {
            throw new IllegalStateException("there is no such post");
        }
        Post post = new Post(userByUsername.get(), Objects.toString((postRepository.findById(postId)).get()));
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        boolean exists = postRepository.existsById(postId);
        if (!exists) {
            throw new IllegalStateException("post with id " + postId + " does not exists");
        }
        postRepository.deleteById(postId);
    }
}

