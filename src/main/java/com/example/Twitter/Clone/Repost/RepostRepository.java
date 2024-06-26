package com.example.Twitter.Clone.Repost;

import com.example.Twitter.Clone.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepostRepository extends JpaRepository<Repost, Long> {
    List<Repost> findByWhoReposted(User user);

    void deleteByPostId(Long postId);

    void deleteByCommentId(Long commentId);

    Repost findByWhoRepostedAndPostId(User myUser, Long postId);

    Repost findByWhoRepostedAndCommentId(User myUser, Long commentId);

    void deleteByWhoRepostedId(Long id);
}
