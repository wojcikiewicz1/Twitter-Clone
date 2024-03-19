package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository <Post, Long> {

    @Query("SELECT COUNT(s) FROM Post s WHERE s.user.id = :id")
    int numberOfPosts (Long id);

    @Query("SELECT COUNT(s) FROM Comment s WHERE s.post.id = :postId")
    int countByPostId(Long postId);

    @Query("SELECT COUNT(s) FROM Like s WHERE s.post.id = :postId")
    int countLikesByPostId(Long postId);

    Post getPostById(Long id);

    List<Post> findByUser(User user);
}
