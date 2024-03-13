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
    private PostService postService;


    public List<Comment> getCommentsByPostId (Long id) {
        return commentRepository.findCommentsByPostId(id);
    }

    public List<Comment> getResponsesByCommentId (Long id) {
        return commentRepository.findResponsesByCommentId(id);
    }

    public Comment getCommentById (Long id) {
        return commentRepository.getCommentById(id);}

    public void AddCommentToPost (Principal principal, Long postId, String body) {
        User myUser = userService.findByUserName(principal.getName());
        Post post = postService.getPostById(postId);
        Comment comment = new Comment();
        comment.setUser(myUser);
        comment.setPost(post);
        comment.setBody(body);
        commentRepository.save(comment);
    }

    public void AddCommentToComment (Principal principal, Long commentId, String body) {
        User myUser = userService.findByUserName(principal.getName());
        Comment comment1 = commentRepository.getCommentById(commentId);
        Comment comment = new Comment();
        comment.setUser(myUser);
        comment.setComment(comment1);
        comment.setBody(body);
        commentRepository.save(comment);
    }

    public List<Comment> getCommentsWithCommentsCount() {
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            int count = commentRepository.countByCommentId(comment.getId());
            comment.setCommentsCount(count);
        }
        return comments;
    }

    public List<Comment> getCommentsWithLikesCount() {
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            int count = commentRepository.countLikesByCommentId(comment.getId());
            comment.setLikesCount(count);
        }
        return comments;
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
