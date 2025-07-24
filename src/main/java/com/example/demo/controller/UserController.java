package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users") // Ensure all user endpoints are under /users
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login") 
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginDTO)
     { boolean isValid = userService.checkLogin(loginDTO);
         if (isValid) { 
            User user = userService.getUserByEmail(loginDTO.getEmail());
         return ResponseEntity.ok(user);
         } else {
             return ResponseEntity.status(401).body("Invalid email or password"); 
        } } 

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping // Added mapping to avoid conflict
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
        User updatedUser = userService.updateUser(email, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload-photo")
    public User uploadUserProfilePhoto(@RequestParam("email") String email,
                                       @RequestParam("file") MultipartFile file) {
        try {
            return userService.saveUserProfilePhoto(email, file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload photo: " + e.getMessage());
        }
    }


    @PostMapping("/changePassword")
    public ResponseEntity<User> changePassword(@RequestParam String email, @RequestParam String newPassword) {
        User updatedUser = userService.changePassword(email, newPassword);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.status(404).body(null);
    }

}
