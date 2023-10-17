package com.example.Twitter.Clone.Like;

import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.User.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data

@Entity
@Table (name = "likes")
public class Like {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private Post post;

    @NonNull
    @ManyToOne
    private User user;

}
