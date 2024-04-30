package com.example.demo.controller;
import com.example.demo.Requests.BookingRequest;
import com.example.demo.Requests.BookingeditRequest;
import com.example.demo.Response.Airor;
import com.example.demo.repository.BookingRepository;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping("/book")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest) {

        if (!bookingService.userExists(bookingRequest.getUserID())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Airor("User does not exist"));
        }

        if (!bookingService.roomExists(bookingRequest.getRoomID())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Airor("Room does not exist"));
        }

        if (!bookingService.isRoomAvailable(bookingRequest.getRoomID(), bookingRequest.getDateOfBooking(), bookingRequest.getTimeFrom(), bookingRequest.getTimeTo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Airor("Room unavailable"));
        }

        if (!bookingService.isValidDateTime(bookingRequest.getDateOfBooking(), bookingRequest.getTimeFrom(), bookingRequest.getTimeTo())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Airor("Invalid date/time"));
        }

        return bookingService.createBooking(bookingRequest);
    }

//    private boolean isValidTimeFormat(String timeFrom) {
//        String timeRegex = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
//        return timeFrom.matches(timeRegex);
//    }

    @DeleteMapping("/book")
    public ResponseEntity<?> deleteBooking(@RequestParam("bookingID") int bookingID) {

        if (!bookingRepository.existsByBookingID(bookingID) ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Airor("Booking does not exist"));
        }
        return bookingService.deleteBooking(bookingID);

    }

    @PatchMapping("/book")
    public ResponseEntity<?> editBooking(@RequestBody BookingeditRequest bookingRequest) {
        if (!bookingService.userExists(bookingRequest.getUserID())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Airor("User does not exist"));
        }
        if (!bookingService.roomExists(bookingRequest.getRoomID())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Airor("Room does not exist"));
        }

        if (!bookingRepository.existsByBookingID(bookingRequest.getBookingID()) ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking does not exist");
        }

        if (!bookingService.isRoomAvailable(bookingRequest.getRoomID(), bookingRequest.getDateOfBooking(), bookingRequest.getTimeFrom(), bookingRequest.getTimeTo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Airor("Room unavailable"));
        }

        if (!bookingService.isValidDateTime(bookingRequest.getDateOfBooking(), bookingRequest.getTimeFrom(), bookingRequest.getTimeTo())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Airor("Invalid date/time"));
        }

        return bookingService.editBooking(bookingRequest);
    }
}

