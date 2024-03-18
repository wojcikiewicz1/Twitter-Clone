package com.example.Twitter.Clone.Like;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository <Like, Long> {

    Like findByUserAndPost(User user, Post post);
    Like findByUserAndComment(User user, Comment comment);
    void deleteByUserId(Long userId);
    void deleteByPostId(Long postId);
    void deleteByCommentId(Long commentId);
}
