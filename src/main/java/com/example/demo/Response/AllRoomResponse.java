package com.example.demo.Response;

import com.example.demo.model.Booking;
import com.example.demo.model.Room;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class AllRoomResponse {

    @JsonProperty("roomID")
    private int roomID;
    @JsonProperty("roomName")
    private String roomName;
    @JsonProperty("capacity")
    private int capacity;
    @JsonProperty("booked")
    List<TempBooking> booked;

    public AllRoomResponse(int roomID, int capacity,List<TempBooking> booked, String roomName) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.capacity = capacity;
        this.booked = booked;
    }
}

