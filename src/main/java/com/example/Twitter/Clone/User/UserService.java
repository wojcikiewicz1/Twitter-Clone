package com.example.Twitter.Clone.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers () {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("user with id " + userId + " does not exists");
        }
        return userRepository.findById(userId);
    }

    public void addNewUser (User user) {
        Optional<User> userOptionalUsername = userRepository.findUserByUsername(user.getUsername());
        Optional<User> userOptionalEmail = userRepository.findUserByEmail(user.getEmail());
        if (userOptionalUsername.isPresent()) {
            throw new IllegalStateException("username is taken");
        }
        if(userOptionalEmail.isPresent()) {
            throw new IllegalStateException("email is taken");
        }
       userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if(!exists) {
            throw new IllegalStateException("user with id " + userId + " does not exists");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, String username, String email, String password) {
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("user with id " + userId + " does not exists"));

        if (!Objects.equals(user.getUsername(), username)) {
            Optional<User> userOptionalUsername = userRepository.findUserByUsername(username);
            if (userOptionalUsername.isPresent()) {
                throw new IllegalStateException("username taken");
            }
            user.setUsername(username);
        }

        if (!Objects.equals(user.getEmail(), email)) {
            Optional<User> userOptionalEmail = userRepository.findUserByEmail(email);
            if (userOptionalEmail.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
            user.setPassword(password);
        }

        user.setPassword(password);
    }


}
