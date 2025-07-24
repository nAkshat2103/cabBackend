package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Booking;
import com.example.demo.model.Trip;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.TripRepository;

import jakarta.transaction.Transactional;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private NotificationService notificationService;

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    @Transactional
    public Booking bookRide(Long tripId, Booking booking) {
        Optional<Trip> tripOpt = tripRepository.findById(tripId);
        if (tripOpt.isPresent()) {
            Trip trip = tripOpt.get();

            if (trip.getNumberOfWindowSeats() >= booking.getNumberOfWindowfSeats()) {
                trip.setNumberOfWindowSeats(trip.getNumberOfWindowSeats() - booking.getNumberOfWindowfSeats());
            } else {
                throw new RuntimeException("Not enough window seats available");
            }

            if (trip.isNonWindowSeatsAvailable() && booking.isBookNonWindowSeats()) {
                trip.setNonWindowSeatsAvailable(false); // Mark non-window seat as booked
            } else if (booking.isBookNonWindowSeats()) {
                throw new RuntimeException("Non-window seat not available");
            }

            // Generate OTP
            String otp = generateOtp();
            booking.setOtp(otp);

            // Save booking
            booking.setTrip(trip);
            booking = bookingRepository.save(booking);

            // Create notifications
            notificationService.createNotification(booking);

            return booking;
        } else {
            throw new RuntimeException("Trip not found");
        }
    }



    @Override
    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public Trip updateTrip(Long id, Trip trip) {
        Trip existingTrip = tripRepository.findById(id).orElse(null);
        if (existingTrip != null) {
            existingTrip.setDestination(trip.getDestination());
            existingTrip.setSource(trip.getSource());
            existingTrip.setDepartureTime(trip.getDepartureTime());
            existingTrip.setArrivalTime(trip.getArrivalTime());
            existingTrip.setCarType(trip.getCarType());
            existingTrip.setNumberOfWindowSeats(trip.getNumberOfWindowSeats());
            existingTrip.setNonWindowSeatsAvailable(trip.isNonWindowSeatsAvailable());
            existingTrip.setPriceWindowSeat(trip.getPriceWindowSeat());
            existingTrip.setPriceNonWindowSeat(trip.getPriceNonWindowSeat());
            existingTrip.setHost(trip.getHost());
            return tripRepository.save(existingTrip);
        }
        return null;
    }

    @Override
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    @Override
    public List<Trip> findTripsByDestinationAndArrivalTime(String destination, LocalDateTime arrivalTime) {
        return tripRepository.findByDestinationAndArrivalTimeBefore(destination, arrivalTime);
    }

     public List<Trip> getTripsByHost(String hostEmail) {
        return tripRepository.findByHost(hostEmail);
    }

    public List<Trip> getTripsByRider(String riderEmail) {
        return bookingRepository.findByRiderEmail(riderEmail)
                .stream()
                .map(Booking::getTrip)
                .collect(Collectors.toList());
    }

    @Override
    public List<Trip> getUpcomingTrips(String email) {
        List<Trip> trips = tripRepository.findByHost(email);
        List<Trip> bookedTrips = bookingRepository.findByRiderEmail(email)
                .stream()
                .map(Booking::getTrip)
                .collect(Collectors.toList());

        trips.addAll(bookedTrips);

        return trips.stream()
                .filter(trip -> trip.getArrivalTime().isAfter(LocalDateTime.now()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Trip> getPastTrips(String email) {
        List<Trip> trips = tripRepository.findByHost(email);
        List<Trip> bookedTrips = bookingRepository.findByRiderEmail(email)
                .stream()
                .map(Booking::getTrip)
                .collect(Collectors.toList());

        trips.addAll(bookedTrips);

        return trips.stream()
                .filter(trip -> trip.getArrivalTime().isBefore(LocalDateTime.now()))
                .distinct()
                .collect(Collectors.toList());
    }
}
