package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.Post.PostService;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;


    public List<Comment> getCommentsByPostId (Long id) {
        return commentRepository.findCommentsByPostId(id);
    }


    public void AddCommentToPost (Principal principal, Long postId, String body) {
        User myUser = userService.findByUserName(principal.getName());
        Post post = postService.getPostById(postId);
        Comment comment = new Comment();
        comment.setUser(myUser);
        comment.setPost(post);
        comment.setBody(body);
        commentRepository.save(comment);
    }

    /**
    public void DeleteComment (Long commentId) {
        boolean exists = commentRepository.existsById(commentId);
        if (!exists) {
            throw new IllegalStateException("comment with id " + commentId + " does not exists");
        }
        commentRepository.deleteById(commentId);

    }

     **/
}
