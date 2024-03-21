package com.example.Twitter.Clone.Repost;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.Post.PostService;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;

@Service
public class RepostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    RepostRepository repostRepository;
    @Autowired
    UserService userService;

    public void repostPost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Repost repost = new Repost();
        repost.setWhoReposted(user);
        repost.setOriginalAuthor(post.getUser());
        repost.setPost(post);
        repost.setRepostTime(new Date());
        repostRepository.save(repost);
    }

    public void unRepostPost(Long postId, Principal principal) {
        User myUser = userService.findByUserName(principal.getName());
        Post postToUnRepost = postRepository.getPostById(postId);

        Repost repost = repostRepository.findByWhoRepostedAndPostId(myUser, postToUnRepost.getId());

        repostRepository.delete(repost);
    }

    @Autowired
    PostService postService;

    public void repostComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

        /*
        Post postFromComment = postService.convertCommentToPost(comment);
        postFromComment.setDateTime(new Date());
        postRepository.save(postFromComment);
*/
        Repost repost = new Repost();
        repost.setWhoReposted(user);
        repost.setOriginalAuthor(comment.getUser());
        repost.setComment(comment);
        repost.setRepostTime(new Date());
        repostRepository.save(repost);
    }

    public void unRepostComment(Long commentId, Principal principal) {
        User myUser = userService.findByUserName(principal.getName());
        Comment commentToUnRepost = commentRepository.getCommentById(commentId);

        Repost repost = repostRepository.findByWhoRepostedAndCommentId(myUser, commentToUnRepost.getId());

        repostRepository.delete(repost);
    }
}
