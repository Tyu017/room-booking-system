package com.example.demo.controller;

import com.example.demo.Response.Airor;
import com.example.demo.model.Booking;
import com.example.demo.model.Room;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookingService;
import com.example.demo.service.RoomService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class History_cont {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingService bookingService;
    private final RoomService roomService;

    public History_cont(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository, BookingService bookingService, RoomService roomService) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;

        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    @GetMapping("/history")
    public ResponseEntity<?> getUpcomingUserBookings(@RequestParam("userID") int userID) {
        if (userRepository.findById(userID).orElse(null) == null) {
            return ResponseEntity.badRequest().body(new Airor("User does not exist"));
        }
        return bookingService.gett(userID);
    }

}

