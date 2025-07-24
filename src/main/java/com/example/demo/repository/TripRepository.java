package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByDestinationAndArrivalTimeBefore(String destination, LocalDateTime arrivalTime);
    List<Trip> findByHost(String host);
}
