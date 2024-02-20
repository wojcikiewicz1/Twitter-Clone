package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table (name = "comments")
public class Comment {

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
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime dateTime;
}
