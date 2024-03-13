package com.example.Twitter.Clone.Like;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository <Like, Long> {

    Like findByUserAndPost(User user, Post post);
    Like findByUserAndComment(User user, Comment comment);
}
