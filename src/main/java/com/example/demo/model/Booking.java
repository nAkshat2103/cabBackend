package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonIgnore
    private Trip trip;

    private String riderEmail;
    private String pickupPoint;
    private int numberOfWindowfSeats;
    private boolean bookNonWindowSeats;
    private String Otp;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getRiderEmail() {
        return riderEmail;
    }

    public void setRiderEmail(String riderEmail) {
        this.riderEmail = riderEmail;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }

    public boolean isBookNonWindowSeats() {
        return bookNonWindowSeats;
    }

    public void setBookNonWindowSeats(boolean bookNonWindowSeats) {
        this.bookNonWindowSeats = bookNonWindowSeats;
    }

    public int getNumberOfWindowfSeats() {
        return numberOfWindowfSeats;
    }

    public void setNumberOfWindowfSeats(int numberOfWindowfSeats) {
        this.numberOfWindowfSeats = numberOfWindowfSeats;
    }
}
