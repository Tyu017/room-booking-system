package com.example.demo.controller;

import com.example.demo.Requests.RoomEditRequest;
import com.example.demo.Requests.RoomRequest;
import com.example.demo.Response.Airor;
import com.example.demo.Response.AllRoomResponse;
import com.example.demo.model.Room;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Room_controller {

    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookRepository;

    public Room_controller(RoomService roomService, RoomRepository roomRepository, UserRepository userRepository, BookingRepository bookRepository) {
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

//    @PostMapping("/add")
@RequestMapping(value = "/rooms",method = RequestMethod.POST)
public ResponseEntity<?> addRoom(@RequestBody RoomRequest roomRequest) {

        if (roomRepository.existsByRoomname(roomRequest.getRoomName())) {
            return ResponseEntity.badRequest().body(new Airor("Room already exists"));
        }
        if (roomRequest.getRoomCapacity() <= 0) {
            return ResponseEntity.badRequest().body(new Airor("Invalid capacity"));
        }
        roomService.addRoom(roomRequest);
        return ResponseEntity.ok("Room created successfully");
    }

    @RequestMapping(value = "/rooms",method = RequestMethod.PATCH)
    public ResponseEntity<?> editRoom(@RequestBody RoomEditRequest roomRequest) {

        if (roomRepository.existsByRoomID(roomRequest.getRoomID())) {
            return ResponseEntity.badRequest().body(new Airor("Room does not exist"));
        }

        if (roomRepository.existsByRoomname(roomRequest.getRoomName())) {
            return ResponseEntity.badRequest().body(new Airor("Room with given name already exists"));
        }

        if (roomRequest.getRoomCapacity() <= 0) {
            return ResponseEntity.badRequest().body(new Airor("Invalid capacity"));
        }

            roomService.editRoom(roomRequest);

        return ResponseEntity.ok("Room edited successfully");

    }

@RequestMapping(value = "/rooms",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRoom(@RequestParam("roomID") int roomId) {
    Room room = roomRepository.findByRoomID(roomId);
    if (room == null) {
        return ResponseEntity.badRequest().body(new Airor("Room does not exist"));
    }
    roomService.deleteRoom(roomId);
            return ResponseEntity.ok("Room deleted successfully");
    }
    @GetMapping("/rooms")
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "capacity", required = false, defaultValue = "0") int capacity) {
        if(capacity < 0) {
            return ResponseEntity.badRequest().body(new Airor("Invalid parameters"));
        }
        List<Room> rooms = roomRepository.findRoomByCapacityGreaterThanEqual(capacity);
        List<AllRoomResponse> userDetailsList = roomService.fromRoomList(rooms,bookRepository);
        return ResponseEntity.ok(userDetailsList);
    }
}

