package com.example.demo.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Booking;
import com.example.demo.model.Trip;
import com.example.demo.service.TripService;

@RestController
@CrossOrigin(origins = "null", allowedHeaders = "*")
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/post")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
        Trip createdTrip = tripService.createTrip(trip);
        return ResponseEntity.ok(createdTrip);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Trip trip = tripService.getTripById(id);
        return ResponseEntity.ok(trip);
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
        Trip updatedTrip = tripService.updateTrip(id, trip);
        return ResponseEntity.ok(updatedTrip);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<Trip>> searchTrips(@RequestBody TripSearchRequest request) {
        List<Trip> trips = tripService.findTripsByDestinationAndArrivalTime(request.getDestination(), request.getArrivalTime());
        return ResponseEntity.ok(trips);
    }

    @PostMapping("/{tripId}/book") 
    public Booking bookRide(@PathVariable Long tripId, @RequestBody Booking booking) 
    { return tripService.bookRide(tripId, booking); }

    public static class TripSearchRequest {
        private String destination;
        private LocalDateTime arrivalTime;

        // Getters and Setters
        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public LocalDateTime getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(LocalDateTime arrivalTime) {
            this.arrivalTime = arrivalTime;
        }
    }

    @GetMapping("/upcoming/{email}")
    public List<Trip> getUpcomingTrips(@PathVariable String email) {
        return tripService.getUpcomingTrips(email);
    }

    @GetMapping("/past/{email}")
    public List<Trip> getPastTrips(@PathVariable String email) {
        return tripService.getPastTrips(email);
    }
}
