package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

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
        Optional<User> userByUsername = userRepository.findUserByUsername(username);
        Optional<Post> postById = postRepository.findById(postId);
        if(userByUsername.isEmpty()) {
            throw new IllegalStateException("there is no such user");
        } else if (postById.isEmpty()) {
            throw new IllegalStateException("there is no such post");
        }
        Comment comment = new Comment(commentBody, userByUsername.get(), postById.get());
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
