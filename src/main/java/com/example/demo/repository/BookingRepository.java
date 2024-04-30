package com.example.demo.repository;

import com.example.demo.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByRoomIDAndDate(int roomID, Date date);
    List<Booking> findByRoomID(int roomID);
    List<Booking> findByUserID(int userID);
    Booking findByBookingID(int bookingID);
    boolean existsByBookingID(int bookingID);

}

