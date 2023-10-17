package com.example.Twitter.Clone.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long> {

    @Query("SELECT s FROM Comment s WHERE s.post.id = :id")
    List<Comment> findCommentsByPostId (Long id);
}
