package com.example.Twitter.Clone.Like;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Comment.CommentService;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostService;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class LikeService {

    @Autowired
    private UserService userService;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    public void likePost(Principal principal, Long postId){
        User myUser = userService.findByUserName(principal.getName());
        Post post = postService.getPostById(postId);

            Like like = new Like();
            like.setUser(myUser);
            like.setPost(post);
            likeRepository.save(like);
    }

    public void likeComment(Principal principal, Long commentId){
        User myUser = userService.findByUserName(principal.getName());
        Comment comment = commentService.getCommentById(commentId);

        Like like = new Like();
        like.setUser(myUser);
        like.setComment(comment);
        likeRepository.save(like);
    }

    public void unlikePost(Principal principal, Long postId) {
        User myUser = userService.findByUserName(principal.getName());
        Post postToUnlike = postService.getPostById(postId);

        Like like = likeRepository.findByUserAndPost(myUser, postToUnlike);

        likeRepository.delete(like);
    }

    public void unlikeComment(Principal principal, Long commentId) {
        User myUser = userService.findByUserName(principal.getName());
        Comment commentToUnlike = commentService.getCommentById(commentId);

        Like like = likeRepository.findByUserAndComment(myUser, commentToUnlike);

        likeRepository.delete(like);
    }

    public boolean isPostLiked(Principal principal, Long postId){
        User myUser = userService.findByUserName(principal.getName());
        Post postToCheck = postService.getPostById(postId);

        Like like = likeRepository.findByUserAndPost(myUser, postToCheck);
        return like != null;
    }

    public boolean isCommentLiked(Principal principal, Long commentId){
        User myUser = userService.findByUserName(principal.getName());
        Comment commentToCheck = commentService.getCommentById(commentId);

        Like like = likeRepository.findByUserAndComment(myUser, commentToCheck);
        return like != null;
    }

}

