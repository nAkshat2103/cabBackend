package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findById(email);
        return user.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(String email, User user) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            user.setEmail(email);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public boolean checkLogin(LoginRequest loginDTO) { 
        User user = getUserByEmail(loginDTO.getEmail()); 
        if (user != null && user.getPassword().equals(loginDTO.getPassword())) { 
            return true;
        } 
         return false;
        }  
        private static final String UPLOAD_DIR = "user-photos/";
        @Transactional // Ensures all operations within this method are executed within a single transaction
    @Override
        public User saveUserProfilePhoto(String email, MultipartFile file) throws IOException {
            // Retrieve the user by email
            User user = userRepository.findByEmail(email)
                                     .orElseThrow(() -> new RuntimeException("User not found"));
    
            // Generate a unique filename
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    
            // Create the directory if it doesn't exist
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
    
            // Save the file to the upload directory
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + fileName));
    
            // Set the profile picture path (or filename) in the User entity
            user.setProfilePicture("E:/CAB_zip/CAB/demo/" + UPLOAD_DIR + fileName);
    
            // Save the updated user entity to the database
            return userRepository.save(user);
        }
    


    @Override
    public User changePassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword);
            return userRepository.save(user);
        }
        return null;
    }
}
