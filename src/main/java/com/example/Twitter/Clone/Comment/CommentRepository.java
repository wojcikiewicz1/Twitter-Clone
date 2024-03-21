package com.example.Twitter.Clone.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long> {

    @Query("SELECT s FROM Comment s WHERE s.post.id = :id")
    List<Comment> findCommentsByPostId (Long id);

    @Query("SELECT s FROM Comment s WHERE s.comment.id = :id")
    List<Comment> findResponsesByCommentId(Long id);

    @Query("SELECT COUNT(s) FROM Comment s WHERE s.comment.id = :commentId")
    int countByCommentId(Long commentId);

    @Query("SELECT COUNT(s) FROM Like s WHERE s.comment.id = :commentId")
    int countLikesByCommentId(Long commentId);

    Comment getCommentById(Long id);

    void deleteByUserId(Long userId);

    void deleteByPostId(Long postId);

    @Query("SELECT COUNT(s) FROM Repost s WHERE s.comment.id = :commentId")
    int countRepostsByCommentId(Long commentId);
}
