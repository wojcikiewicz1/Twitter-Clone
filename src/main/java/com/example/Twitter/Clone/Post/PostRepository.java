package com.example.Twitter.Clone.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {

    @Query("SELECT s FROM Post s WHERE s.user.id = :id ORDER BY s.dateTime DESC")
    List<Post> findPostsByUserId (Long id);


    @Query("SELECT p " +
            "FROM Post p " +
            "INNER JOIN Follower f " +
            "ON p.user.id = f.following.id " +
            "WHERE f.user.id = :id " +
            "ORDER BY p.dateTime DESC")
    List<Post> findPostsByFollowings (Long id);

}
