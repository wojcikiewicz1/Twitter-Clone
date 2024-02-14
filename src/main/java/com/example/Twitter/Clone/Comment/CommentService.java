package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private UserService userService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;


    public List<Comment> getCommentsByPostId (Long id) {
        return commentRepository.findCommentsByPostId(id);
    }

    public void AddCommentToPost (String username, Long postId, String commentBody) {
        User userByUsername = userService.findByUserName(username);
        Optional<Post> postById = postRepository.findById(postId);

        Comment comment = new Comment(commentBody, userByUsername, postById.get());
        commentRepository.save(comment);
    }

    public void DeleteComment (Long commentId) {
        boolean exists = commentRepository.existsById(commentId);
        if (!exists) {
            throw new IllegalStateException("comment with id " + commentId + " does not exists");
        }
        commentRepository.deleteById(commentId);

    }
}
