package com.example.Twitter.Clone.Post;

import com.example.Twitter.Clone.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String content;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date dateTime;
    @Transient
    private int commentsCount;
    @Transient
    private int likesCount;
    @Transient
    private boolean isReposted;
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private Set<Repost> reposts = new HashSet<>();
}
