package com.example.Twitter.Clone.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {

    @Query("SELECT s FROM Post s WHERE s.user.username = :username ORDER BY s.dateTime DESC")
    List<Post> findPostsByUsername (String username);

    @Query("SELECT p " +
            "FROM Post p " +
            "INNER JOIN Follower f " +
            "ON p.user.id = f.userToFollow.id " +
            "WHERE f.user.username = :username " +
            "ORDER BY p.dateTime DESC")
    List<Post> findPostsByFollowings (String username);

    @Query("SELECT COUNT(s) FROM Post s WHERE s.user.id = :id")
    int numberOfPosts (Long id);

    @Query("SELECT COUNT(s) FROM Comment s WHERE s.post.id = :postId")
    int countByPostId(Long postId);

    Post getPostById(Long id);

}
