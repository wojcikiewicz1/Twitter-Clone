package com.example.Twitter.Clone.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping (path = "/users")
    public ResponseEntity <List<User>> getUsers () {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping (path = "/users/{userId}")
    public ResponseEntity <Optional<User>> getUserById (@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping (path = "/users")
    public ResponseEntity registerNewUser (@RequestBody User user){
        userService.addNewUser(user);
        return ResponseEntity.ok("User added");
    }

    @DeleteMapping (path = "/users/{userId}")
    public ResponseEntity deleteUser (@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted");
    }

    //aktualizowanie - http://localhost:8080/users/1?name=Maria
    @PutMapping(path = "/users/{userId}")
    public ResponseEntity updateUser (@PathVariable("userId") Long userId,
                            @RequestParam(required = false) String username,
                            @RequestParam(required = false) String email,
                            @RequestParam(required = false) String password) {
        userService.updateUser(userId, username, email, password);
        return ResponseEntity.ok("User updated");
    }
}
