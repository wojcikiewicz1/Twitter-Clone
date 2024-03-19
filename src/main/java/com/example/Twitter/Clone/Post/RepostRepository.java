package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepostRepository extends JpaRepository<Repost, Long> {
    List<Repost> findByWhoReposted(User user);

    Repost findByWhoRepostedAndPostId(User myUser, Long postId);
}
