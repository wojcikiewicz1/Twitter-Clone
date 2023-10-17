package com.example.Twitter.Clone.Post;

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
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private User user;
    @NonNull
    private String body;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime dateTime;

    public Post(User user, Post post) {

    }
}
