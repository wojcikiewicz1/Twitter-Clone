package com.example.Twitter.Clone.User;

import com.example.Twitter.Clone.Role.Role;
import com.example.Twitter.Clone.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser (User user) {
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setEmail(user.getEmail());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());

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

    public List<User> findRandomUsers(String principalUsername, int number) {
        List<User> allUsers = userRepository.findAll();
        allUsers.removeIf(user -> user.getUsername().equals(principalUsername));

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

    public void updateUser (User user) {
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
