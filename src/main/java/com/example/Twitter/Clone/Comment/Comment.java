package com.example.Twitter.Clone.Comment;

import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data

@Entity
@Table (name = "comments")
public class Comment {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String body;
    @NonNull
    @ManyToOne
    private User user;
    @NonNull
    @ManyToOne
    private Post post;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime dateTime;
}
