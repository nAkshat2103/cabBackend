package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "null", allowedHeaders = "*")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{email}")
    public List<Notification> getNotifications(@PathVariable String email) {
        return notificationService.getNotifications(email);
    }
}
