package com.example.Twitter.Clone.User;

import com.example.Twitter.Clone.AuthController.AuthController;
import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Comment.CommentService;
import com.example.Twitter.Clone.Follower.FollowerRepository;
import com.example.Twitter.Clone.Follower.FollowerService;
import com.example.Twitter.Clone.Like.LikeService;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.Post.PostService;
import com.example.Twitter.Clone.Post.TimelineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{username}")
    public String profile(@PathVariable("username") String username, Model model, Principal principal) {
        String principalUsername = principal.getName();
        User myUser = userService.findByUserName(principalUsername);
        boolean isOwner = username.equals(principalUsername);
        model.addAttribute("isOwner", isOwner);

        List<Post> posts;
        List<Comment> comments;
        if (isOwner) {
            posts = postService.getAllUserPostsAndReposts(principalUsername);
            comments = commentService.getAllUserRepostedComments(principalUsername);
        } else {
            User user = userService.findByUserName(username);
            if (user != null) {
                posts = postService.getAllUserPostsAndReposts(username);
                comments = commentService.getAllUserRepostedComments(username);
            } else {
                return "redirect:/error";
            }
        }

        List<TimelineItem> timelineItems = new ArrayList<>();
        timelineItems.addAll(posts);
        timelineItems.addAll(comments);
        timelineItems.sort((item1, item2) -> {
            Date date1 = item1.getRepostTime() != null ? item1.getRepostTime() : item1.getDateTime();
            Date date2 = item2.getRepostTime() != null ? item2.getRepostTime() : item2.getDateTime();

            return date2.compareTo(date1);
        });
        model.addAttribute("timelineItems", timelineItems);

        for (Post post : posts) {
            int commentsCount = postRepository.countByPostId(post.getId());
            post.setCommentsCount(commentsCount);
        }
        model.addAttribute("posts", posts);

        Map<Long, Boolean> isLikedMap = new HashMap<>();
        Map<Long, Boolean> isRepostedMap = new HashMap<>();
        for (Post post : posts) {
            boolean isLiked = likeService.isPostLiked(principal, post.getId());
            isLikedMap.put(post.getId(), isLiked);
            boolean isReposted = postService.isPostRepostedByUser(principal, post.getId());
            isRepostedMap.put(post.getId(), isReposted);;
        }
        List<Post> postsWithLikes = postService.getPostsWithLikesCount();
        List<Post> postsWithReposts = postService.getPostsWithRepostsCount();

        model.addAttribute("isLikedMap", isLikedMap);
        model.addAttribute("isRepostedMap", isRepostedMap);
        model.addAttribute("postsWithLikes", postsWithLikes);
        model.addAttribute("postsWithReposts", postsWithReposts);

        for (Comment comment : comments) {
            int commentsCount = commentRepository.countByCommentId(comment.getId());
            comment.setCommentsCount(commentsCount);
        }
        model.addAttribute("comments", comments);

        Map<Long, Boolean> isLikedMapComments = new HashMap<>();
        Map<Long, Boolean> isRepostedMapComments = new HashMap<>();
        for (Comment comment : comments) {
            boolean isLikedComment = likeService.isCommentLiked(principal, comment.getId());
            isLikedMapComments.put(comment.getId(), isLikedComment);
            boolean isRepostedComment = commentService.isCommentRepostedByUser(principal, comment.getId());
            isRepostedMapComments.put(comment.getId(), isRepostedComment);
        }
        List<Comment> commentsWithLikes = commentService.getCommentsWithLikesCount();
        List<Comment> commentsWithReposts = commentService.getCommentsWithRepostsCount();

        model.addAttribute("isLikedMapComments", isLikedMapComments);
        model.addAttribute("isRepostedMapComments", isRepostedMapComments);
        model.addAttribute("commentsWithLikes", commentsWithLikes);
        model.addAttribute("commentsWithReposts", commentsWithReposts);

        User user = userService.findByUserName(username);
        model.addAttribute("user", user);
        model.addAttribute("myUser", myUser);

        boolean isFollowingMain = followerService.isFollowing(principal, username);
        model.addAttribute("isFollowingMain", isFollowingMain);

        int followers = followerRepository.followers(user.getId());
        int following = followerRepository.following(user.getId());
        model.addAttribute("following", following);
        model.addAttribute("followers", followers);

        int numberOfPosts = posts.size() + comments.size();
        model.addAttribute("numberOfPosts", numberOfPosts);

        AuthController.randomUsers(model, principal, username, principalUsername, userService, followerService);

        isUserFollowed(model, principal, userRepository, followerService);

        return "profile";
    }

    public static void isUserFollowed(Model model, Principal principal, UserRepository userRepository, FollowerService followerService){
        List<User> users = userRepository.findAll();
        Map<String, Boolean> isFollowingMap = new HashMap<>();
        for (User someUser : users) {
            boolean isFollowing = followerService.isFollowing(principal, someUser.getUsername());
            isFollowingMap.put(someUser.getUsername(), isFollowing);
        }
        model.addAttribute("users", users);
        model.addAttribute("isFollowingMap", isFollowingMap);
    }

    @PostMapping("/deleteUser")
    public String deleteUser(Principal principal) {
        User user = userService.findByUserName(principal.getName());
        userService.deleteUser(user);
        return "redirect:/logout";
    }

    @GetMapping("/api/updateUserData")
    public String updateUserData (Principal principal, Model model) {
        User myUser = userService.findByUserName(principal.getName());
        model.addAttribute("myUser", myUser);
        return "redirect:/" + principal.getName();
    }

    @PostMapping("/api/updateUserData")
    public String updateUserData (@ModelAttribute("user") User user, Principal principal, Model model) {
        User myUser = userService.findByUserName(principal.getName());
        model.addAttribute("myUser", myUser);

        myUser.setBio(user.getBio());
        myUser.setLocation(user.getLocation());
        myUser.setDateOfBirth(user.getDateOfBirth());
        myUser.setWebsite(user.getWebsite());

        userService.updateUserData(myUser);
        return "redirect:/" + principal.getName();
    }

}