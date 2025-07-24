package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if (existingBooking != null) {
            existingBooking.setTrip(booking.getTrip());
            existingBooking.setRiderEmail(booking.getRiderEmail());
            // existingBooking.setPickUpPoint(booking.getPickUpPoint());
            // existingBooking.setVerified(booking.isVerified());
            return bookingRepository.save(existingBooking);
        }
        return null;
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public void verifyBooking(Booking booking) {
        Booking existingBooking = bookingRepository.findById(booking.getId()).orElse(null);
        if (existingBooking != null) {
            // existingBooking.setVerified(true);
            bookingRepository.save(existingBooking);
        }
    }
}