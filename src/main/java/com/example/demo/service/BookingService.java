package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Booking;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking getBookingById(Long id);
    List<Booking> getAllBookings();
    Booking updateBooking(Long id, Booking booking);
    void deleteBooking(Long id);
    void verifyBooking(Booking booking); // Add this method
}
