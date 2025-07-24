package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, String> {

   
    // Additional custom queries can be defined here

    public Optional<User> findByEmail(String email);

    

    public void deleteByEmail(String email);
}
