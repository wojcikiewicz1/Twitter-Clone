package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.Follower.FollowerRepository;
import com.example.Twitter.Clone.Like.Like;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
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
    @Autowired
    private UserService userService;

    public Post getPostById(Long postId) {
        return postRepository.getPostById(postId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsAndRepostsByFollowings(String username) {

        User user = userRepository.findByUsername(username);
        List<User> followingUsers = followerRepository.findFollowingsByUser(user);

        Map<Long, Post> uniquePosts = new LinkedHashMap<>();

        for (User followingUser : followingUsers) {

            List<Post> originalPosts = postRepository.findByUser(followingUser);
            for (Post post : originalPosts) {
                uniquePosts.putIfAbsent(post.getId(), post);
            }

            List<Repost> reposts = repostRepository.findByWhoReposted(followingUser);
            for (Repost repost : reposts) {
                Post repostedPost = repost.getPost();
                uniquePosts.putIfAbsent(repostedPost.getId(), repostedPost);
            }
        }

        List<Post> allPosts = new ArrayList<>(uniquePosts.values());

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

    public List<Post> getPostsWithRepostsCount() {
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            int count = postRepository.countRepostsByPostId(post.getId());
            post.setRepostsCount(count);
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

    public void unRepost(Long postId, Principal principal) {
        User myUser = userService.findByUserName(principal.getName());
        Post postToUnRepost = getPostById(postId);

        Repost repost = repostRepository.findByWhoRepostedAndPostId(myUser, postToUnRepost.getId());

        repostRepository.delete(repost);
    }


    public boolean isPostRepostedByUser(Principal principal, Long postId) {
        User myUser = userService.findByUserName(principal.getName());
        Post postToCheck = getPostById(postId);

        Repost repost = repostRepository.findByWhoRepostedAndPostId(myUser, postToCheck.getId());
        return repost !=null;
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

