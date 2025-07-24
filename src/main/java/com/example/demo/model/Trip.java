package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String host;

    private String carType;
    private int numberOfWindowSeats;
    private boolean nonWindowSeatsAvailable;
    private double priceWindowSeat;
    private double priceNonWindowSeat;
   
   
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private List<Booking> bookings;
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public int getNumberOfWindowSeats() {
        return numberOfWindowSeats;
    }

    public void setNumberOfWindowSeats(int numberOfWindowSeats) {
        this.numberOfWindowSeats = numberOfWindowSeats;
    }

    public boolean isNonWindowSeatsAvailable() {
        return nonWindowSeatsAvailable;
    }

    public void setNonWindowSeatsAvailable(boolean nonWindowSeatsAvailable) {
        this.nonWindowSeatsAvailable = nonWindowSeatsAvailable;
    }

    public double getPriceWindowSeat() {
        return priceWindowSeat;
    }

    public void setPriceWindowSeat(double priceWindowSeat) {
        this.priceWindowSeat = priceWindowSeat;
    }

    public double getPriceNonWindowSeat() {
        return priceNonWindowSeat;
    }

    public void setPriceNonWindowSeat(double priceNonWindowSeat) {
        this.priceNonWindowSeat = priceNonWindowSeat;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
