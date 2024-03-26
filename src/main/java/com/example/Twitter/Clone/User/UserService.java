package com.example.Twitter.Clone.User;

import com.example.Twitter.Clone.Comment.CommentRepository;
import com.example.Twitter.Clone.Follower.FollowerRepository;
import com.example.Twitter.Clone.Like.LikeRepository;
import com.example.Twitter.Clone.Post.PostRepository;
import com.example.Twitter.Clone.Role.Role;
import com.example.Twitter.Clone.Role.RoleRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FollowerRepository followerRepository;

    public void saveUser (User user) {
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setEmail(user.getEmail());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setJoinedDate(LocalDate.now());

        Role role = roleRepository.findByName("USER");
        if(role == null){
            role = checkRoleExist();
        }
        user1.setRoles(Arrays.asList(role));
        userRepository.save(user1);
    }

    private Role checkRoleExist (){
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }

    public User findByUserName (String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findRandomUsers(String principalUsername, String currentProfileUsername, int number) {
        List<User> allUsers = userRepository.findAll();
        allUsers.removeIf(user -> user.getUsername().equals(principalUsername) || user.getUsername().equals(currentProfileUsername));

        if (allUsers.size() <= number) {
            return allUsers;
        }

        List<User> randomUsers = new ArrayList<>();
        while (randomUsers.size() < number) {
            int index = (int) (Math.random() * allUsers.size());
            User randomUser = allUsers.get(index);

            if (!randomUsers.contains(randomUser)) {
                randomUsers.add(randomUser);
            }
        }
        return randomUsers;
    }
    @Transactional
    public void deleteUser(User user) {

        user.getComments().forEach(comment -> likeRepository.deleteByCommentId(comment.getId()));

        user.getPosts().forEach(post -> {
            likeRepository.deleteByPostId(post.getId());
            commentRepository.deleteByPostId(post.getId());
        });

        commentRepository.deleteByUserId(user.getId());

        likeRepository.deleteByUserId(user.getId());

        followerRepository.deleteByUserIdOrUserToFollowId(user.getId(), user.getId());

        postRepository.deleteAll(user.getPosts());

        userRepository.delete(user);
    }
    public void updateUser (User user) {
        userRepository.save(user);
    }

    public void updateUserData (User user) {
        user.setBio(user.getBio());
        user.setLocation(user.getLocation());
        user.setDateOfBirth(user.getDateOfBirth());

        String website = user.getWebsite();
        if (StringUtils.isNotEmpty(website) && !website.startsWith("http://") && !website.startsWith("https://")) {
            website = "https://" + website;
            user.setWebsite(website);
        }

        userRepository.save(user);
    }

    public boolean verifyUserPassword (User user, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, user.getPassword());
    }

    public void changePassword (User user, String newPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }
}
