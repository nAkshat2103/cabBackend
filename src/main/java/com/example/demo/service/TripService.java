package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.model.Booking;
import com.example.demo.model.Trip;

public interface TripService {
    Trip createTrip(Trip trip);
    Trip getTripById(Long id);
    List<Trip> getAllTrips();
    Trip updateTrip(Long id, Trip trip);
    void deleteTrip(Long id);
    public Booking bookRide(Long tripId, Booking booking);
    List<Trip> findTripsByDestinationAndArrivalTime(String destination, LocalDateTime arrivalTime);
    List<Trip> getUpcomingTrips(String email);
    List<Trip> getPastTrips(String email);}
