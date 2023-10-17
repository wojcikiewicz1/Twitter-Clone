package com.example.Twitter.Clone.Follower;

import com.example.Twitter.Clone.User.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data

@Entity
@Table(name = "followers")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    @ManyToOne
    private User following;

}
