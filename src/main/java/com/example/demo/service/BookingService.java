package com.example.demo.service;

import com.example.demo.Requests.BookingRequest;
import com.example.demo.Requests.BookingeditRequest;
import com.example.demo.Response.Userres;
import com.example.demo.model.Booking;
import com.example.demo.model.Room;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(RoomRepository roomRepository, UserRepository userRepository,BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public ResponseEntity<String> createBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setUserID(bookingRequest.getUserID());
        booking.setRoomID(bookingRequest.getRoomID());
        booking.setDate(bookingRequest.getDateOfBooking());
        booking.setStartTime(bookingRequest.getTimeFrom());
        booking.setEndTime(bookingRequest.getTimeTo());
        booking.setPurpose(bookingRequest.getPurpose());
        bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body("Booking created successfully");
    }

    public ResponseEntity<String> deleteBooking(int bookingID) {
        bookingRepository.deleteById(bookingID);
        return ResponseEntity.status(HttpStatus.OK).body("Booking deleted successfully");
    }

    // Method to check if the room exists
    public boolean roomExists(int roomID) {
        return roomRepository.existsById(roomID);
    }


    // Method to check if the user exists
    public boolean userExists(int userID) {
        return userRepository.existsById(userID);
    }

    // Method to validate date and time
    public boolean isValidDateTime(Date date, String timeFrom, String timeTo) {
        // Check if date is in the past
        Date currentDate = new Date();
        if (date.before(currentDate)) {
            return false;
        }

        // Check if timeFrom is before timeTo
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date startTime = sdf.parse(timeFrom);
            Date endTime = sdf.parse(timeTo);
            if (startTime.after(endTime)) {
                return false;
            }
        } catch (ParseException e) {
            return false; // Invalid time format
        }
        return true;
    }

    // Method to check room availability
    public boolean isRoomAvailable(int roomId, Date date, String timeFrom, String timeTo) {
        // Parse time strings to Date objects
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime, endTime;
        try {
            startTime = sdf.parse(timeFrom);
            endTime = sdf.parse(timeTo);
        } catch (ParseException e) {
            return false;
        }

        // Get existing bookings for the specified room and date
        List<Booking> bookings = bookingRepository.findByRoomIDAndDate(roomId, date);


        // Check if there are any bookings for the specified time range
        for (Booking booking : bookings) {
            // Parse booking start and end times
            Date bookingStartTime, bookingEndTime;
            try {
                bookingStartTime = sdf.parse(booking.getStartTime());
                bookingEndTime = sdf.parse(booking.getEndTime());
            } catch (ParseException e) {
                // Invalid time format in existing booking
                return false;
            }


            // Check for overlapping time ranges
            if (startTime.before(bookingEndTime) && endTime.after(bookingStartTime)) {
                // There is a booking that overlaps with the specified time range
                return false;
            }
        }
        return true;
    }

    public ResponseEntity<String> editBooking(BookingeditRequest bookingRequest) {

            Booking booking = bookingRepository.findByBookingID(bookingRequest.getBookingID());
            booking.setUserID(bookingRequest.getUserID());
            booking.setRoomID(bookingRequest.getRoomID());
            booking.setDate(bookingRequest.getDateOfBooking());
            booking.setStartTime(bookingRequest.getTimeFrom());
            booking.setEndTime(bookingRequest.getTimeTo());
            booking.setPurpose(bookingRequest.getPurpose());

            bookingRepository.save(booking);
            return ResponseEntity.ok().body("Booking modified successfully");

    }

    public List<Booking> getUpcomingBookingsForUser(int userID) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Booking> allBookings = bookingRepository.findByUserID(userID);
        return allBookings.stream()
                .filter(booking -> booking.getDate().toInstant().isAfter(Instant.from(currentDateTime)))
                .collect(Collectors.toList());
    }
    public ResponseEntity<?> gett(int userID){
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Booking> allBookings = bookingRepository.findAll();
        List<Object> upcomingBookings = allBookings.stream()
                .filter(booking -> booking.getUserID() == userID)
                .filter(booking -> {
                    LocalDate bookingDate = booking.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalTime bookingStartTime = LocalTime.parse(booking.getStartTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                    LocalDateTime bookingDateTime = LocalDateTime.of(bookingDate, bookingStartTime);
                    return bookingDateTime.isBefore(currentDateTime);
                })
                .map(booking -> {
                    Room rooomm = roomRepository.findByRoomID(booking.getRoomID());
                    return new Object() {
                        public String room = rooomm.getRoomname();
                        public int roomID = booking.getRoomID();
                        public int bookingID = booking.getBookingID();
                        @JsonFormat(pattern = "yyyy-MM-dd")
                        public Date dateOfBooking = booking.getDate();
                        public String timeFrom = booking.getStartTime();
                        public String timeTo = booking.getEndTime();
                        public String purpose = booking.getPurpose();
                    };
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(upcomingBookings);
    }
    public ResponseEntity<?> geto(int userID){
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<Booking> allBookings = bookingRepository.findAll();
        List<Object> upcomingBookings = allBookings.stream()
                .filter(booking -> booking.getUserID() == userID)
                .filter(booking -> {
                    LocalDate bookingDate = booking.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalTime bookingStartTime = LocalTime.parse(booking.getStartTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                    LocalDateTime bookingDateTime = LocalDateTime.of(bookingDate, bookingStartTime);
                    return bookingDateTime.isAfter(currentDateTime);
                })
                .map(booking -> {
                    Room rooomm = roomRepository.findByRoomID(booking.getRoomID());
                    return new Object() {
                        public final String room = rooomm.getRoomname();
                        public final int roomID = booking.getRoomID();
                        public final int bookingID = booking.getBookingID();
                        public final LocalDate dateOfBooking = booking.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        public final String timeFrom = booking.getStartTime();
                        public final String timeTo = booking.getEndTime();
                        public final String purpose = booking.getPurpose();
                        public final Userres user=new Userres(booking.getUserID());
                    };
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(upcomingBookings);

    }

}

