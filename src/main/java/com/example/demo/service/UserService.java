package com.example.demo.service;


import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.LoginRequest;
import com.example.demo.model.User;

public interface UserService {
    User createUser(User user);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUser(String email, User user);
    void deleteUser(String email);
    public boolean checkLogin(LoginRequest loginDTO);
    public User saveUserProfilePhoto(String email, MultipartFile file) throws IOException;
    
    User changePassword(String email, String newPassword);
}
