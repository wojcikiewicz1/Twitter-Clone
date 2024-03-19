package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Follower.FollowerRepository;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RepostRepository repostRepository;
    @Autowired
    private FollowerRepository followerRepository;

    public Post getPostById(Long postId) {
        return postRepository.getPostById(postId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsAndRepostsByFollowings(String username) {

        User user = userRepository.findByUsername(username);
        List<User> followingUsers = followerRepository.findFollowingsByUser(user);

        List<Post> allPosts = new ArrayList<>();

        for (User followingUser : followingUsers) {

            allPosts.addAll(postRepository.findByUser(followingUser));

            List<Repost> reposts = repostRepository.findByWhoReposted(followingUser);
            List<Post> repostedPosts = reposts.stream()
                    .map(Repost::getPost)
                    .collect(Collectors.toList());
            allPosts.addAll(repostedPosts);
        }

        allPosts.sort(Comparator.comparing(Post::getDateTime).reversed());

        return allPosts;
    }

    public void addNewPost(Principal principal, String content) {
        User user = userRepository.findByUsername(principal.getName());
        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        post.setReposted(false);
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

    public void repost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Repost repost = new Repost();
        repost.setWhoReposted(user);
        repost.setOriginalAuthor(post.getUser());
        repost.setPost(post);
        repost.setRepostTime(new Date());
        repostRepository.save(repost);
    }

    public List<Post> getAllUserPostsAndReposts(String username) {
        User user = userRepository.findByUsername(username);

        List<Post> posts = postRepository.findByUser(user);
        List<Repost> reposts = repostRepository.findByWhoReposted(user);

        List<Post> repostedPosts = reposts.stream()
                .map(Repost::getPost)
                .collect(Collectors.toList());

        List<Post> allPosts = new ArrayList<>(posts);
        allPosts.addAll(repostedPosts);
        allPosts.sort(Comparator.comparing(Post::getDateTime).reversed());

        return allPosts;
    }

/**
    public void deletePost(Long postId) {

        postRepository.deleteById(postId);
    }
     **/
}

