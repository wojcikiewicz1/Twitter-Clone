package com.example.Twitter.Clone.Repost;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "reposts")
public class Repost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "original_author_id")
    private User originalAuthor;
    @ManyToOne
    @JoinColumn(name = "who_reposted_id")
    private User whoReposted;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @Column(name = "repost_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repostTime;
}