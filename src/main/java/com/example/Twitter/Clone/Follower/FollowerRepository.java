package com.example.Twitter.Clone.Follower;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository <Follower, Long> {

    @Query("SELECT s FROM Follower s WHERE s.user.id = :id")
    List<Follower> findFollowingsByUserId (Long id);
}
