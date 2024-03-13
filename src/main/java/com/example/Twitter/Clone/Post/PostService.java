package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Comment.CommentService;
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

    @Autowired
    private CommentRepository commentRepository;

    public Post getPostById(Long postId) {
        return postRepository.getPostById(postId);
    }

    public List<Post> getPostsByUsername(String username) {

        return postRepository.findPostsByUsername(username);
    }

    public List<Post> getPostsByFollowings (String username) {

        return postRepository.findPostsByFollowings(username);
    }

    public void addNewPost(Principal principal, String content) {
        User user = userRepository.findByUsername(principal.getName());
        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        postRepository.save(post);
    }

    public List<Post> getPostsWithCommentsCount() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            int count = postRepository.countByPostId(post.getId());
            post.setCommentsCount(count);
        }
        return posts;
    }

    public List<Post> getPostsWithLikesCount() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            int count = postRepository.countLikesByPostId(post.getId());
            post.setLikesCount(count);
        }
        return posts;
    }


    /**
    public void sharePost(Principal principal, Long postId) {
        User user = userService.findByUserName(principal.getName());
        Optional<Post> optionalPost = postRepository.findById(postId);

        Post post = new Post(user, Objects.toString((postRepository.findById(postId)).get()));
        postRepository.save(post);
    }

    public void deletePost(Long postId) {

        postRepository.deleteById(postId);
    }
     **/
}

