package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Booking;
import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    @Transactional
    public void createNotification(Booking booking) {
        Notification notification = new Notification();
        notification.setEmail(booking.getRiderEmail());
        notification.setMessage("Your OTP for booking is: " + booking.getOtp());
        notification.setTripId(booking.getTrip().getId());
        notificationRepository.save(notification);

        Notification hostNotification = new Notification();
        hostNotification.setEmail(booking.getTrip().getHost());
        hostNotification.setMessage("A new booking has been made. OTP: " + booking.getOtp());
        hostNotification.setTripId(booking.getTrip().getId());
        notificationRepository.save(hostNotification);
    }


    public List<Notification> getNotifications(String email) {
        List<Notification> notifications = notificationRepository.findByEmail(email);
        System.out.println(notifications.size());
        return notifications;
    }
}