package com.example.Twitter.Clone.Follower;

import com.example.Twitter.Clone.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository <Follower, Long> {

    @Query("SELECT s FROM Follower s WHERE s.user.username = :username")
    List<Follower> findFollowingsByUsername (String username);

    @Query("SELECT COUNT(s) FROM Follower s WHERE s.user.id = :id")
    int following (Long id);

    @Query("SELECT COUNT(s) FROM Follower s WHERE s.userToFollow.id = :id")
    int followers (Long id);

    Follower findByUserAndUserToFollow(User user, User userToUnfollow);

    void deleteByUserIdOrUserToFollowId(Long userId, Long userToFollowId);
}
