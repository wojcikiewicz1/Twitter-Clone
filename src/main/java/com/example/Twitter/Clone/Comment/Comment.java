package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.Post.TimelineItem;
import com.example.Twitter.Clone.Repost.Repost;
import com.example.Twitter.Clone.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table (name = "comments")
public class Comment implements TimelineItem {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String body;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date dateTime;
    @Transient
    private int commentsCount;
    @Transient
    private int likesCount;
    @Transient
    private int repostsCount;
    @Transient
    private boolean isReposted;
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private Set<Repost> reposts = new HashSet<>();
    @Column(name = "reposted_at")
    private Date repostTime;
    @Column(name = "is_pinned")
    private boolean isPinned = false;
    @Column(name = "gif_url")
    private String gifUrl;


    @Override
    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public Date getRepostTime() {
        return repostTime;
    }

    @Override
    public String getGifUrl() {
        return gifUrl;
    }


    public String getType() {
        return "Comment";
    }
}
