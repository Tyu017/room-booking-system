package com.example.demo.controller;
import com.example.demo.Response.Airor;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookingService;
import com.example.demo.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class Upcoming_cont {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingService bookingService;
    private final RoomService roomService;

    public Upcoming_cont(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository, BookingService bookingService, RoomService roomService) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
        this.roomService = roomService;
    }
    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingUserBookings(@RequestParam("userID") int userID) {
        if (userRepository.findById(userID).orElse(null) == null) {
            return ResponseEntity.badRequest().body(new Airor("User does not exist"));
        }
        return bookingService.geto(userID);
    }

}

