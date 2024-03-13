package com.example.Twitter.Clone.Like;

import com.example.Twitter.Clone.Comment.Comment;
import com.example.Twitter.Clone.Post.Post;
import com.example.Twitter.Clone.User.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table (name = "likes")
public class Like {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
