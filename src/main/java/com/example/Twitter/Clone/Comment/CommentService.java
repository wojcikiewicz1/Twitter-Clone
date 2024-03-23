package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Like.LikeRepository;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.PostService;
import com.example.Twitter.Clone.Repost.Repost;
import com.example.Twitter.Clone.Repost.RepostRepository;
import com.example.Twitter.Clone.User.User;
import com.example.Twitter.Clone.User.UserRepository;
import com.example.Twitter.Clone.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private RepostRepository repostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;


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

    @Transactional
    public void deleteComment (Comment comment) {
        likeRepository.deleteByCommentId(comment.getId());
        commentRepository.deleteByCommentId(comment.getId());
        commentRepository.delete(comment);
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

    public List<Comment> getCommentsWithRepostsCount() {
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            int count = commentRepository.countRepostsByCommentId(comment.getId());
            comment.setRepostsCount(count);
        }
        return comments;
    }

    public boolean isCommentRepostedByUser(Principal principal, Long commentId) {
        User myUser = userService.findByUserName(principal.getName());
        Comment commentToCheck = getCommentById(commentId);

        Repost repost = repostRepository.findByWhoRepostedAndCommentId(myUser, commentToCheck.getId());
        return repost !=null;
    }

    public List<Comment> getAllUserRepostedComments(String username) {
        User user = userRepository.findByUsername(username);

        List<Repost> reposts = repostRepository.findByWhoReposted(user);

        List<Comment> repostedComments = reposts.stream()
                .filter(repost -> repost.getComment() != null)
                .map(Repost::getComment)
                .collect(Collectors.toList());

        List<Comment> comments = new ArrayList<>(repostedComments);

        return comments;
    }

}
