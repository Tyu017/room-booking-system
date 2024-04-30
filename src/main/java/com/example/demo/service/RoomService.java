package com.example.demo.service;

import com.example.demo.Requests.RoomEditRequest;
import com.example.demo.Requests.RoomRequest;
import com.example.demo.Response.AllRoomResponse;
import com.example.demo.Response.TempBooking;
import com.example.demo.Response.Userres;
import com.example.demo.model.Booking;
import com.example.demo.model.Room;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class RoomService {

    @Autowired
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public RoomService(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }
    public void addRoom(RoomRequest roomRequest) {

        Room room = new Room();
        room.setCapacity(roomRequest.getRoomCapacity());
        room.setRoomname(roomRequest.getRoomName());
        roomRepository.save(room);
    }

    public void editRoom(RoomEditRequest roomEditRequest) {
        Room room = roomRepository.findByRoomID(roomEditRequest.getRoomID());
        room.setRoomname(roomEditRequest.getRoomName());
        room.setCapacity(roomEditRequest.getRoomCapacity());
        roomRepository.save(room);
    }

    public void deleteRoom(int roomId) {
        Room room = roomRepository.findByRoomID(roomId);
        roomRepository.delete(room);
    }

    public List<AllRoomResponse> fromRoomList(List<Room> roomList, BookingRepository bookingRepository) {

        List<AllRoomResponse> allRoomResponses = new ArrayList<>();
        for (Room room : roomList) {
            List<Booking> bookingList = bookingRepository.findByRoomID(room.getRoomID());
            AllRoomResponse roomResponse = new AllRoomResponse(room.getRoomID(), room.getCapacity(), getTempBookings(bookingList),room.getRoomname());
            allRoomResponses.add(roomResponse);
        }
        return allRoomResponses;
    }
    private List<TempBooking> getTempBookings(List<Booking> bookingList) {
        List<TempBooking> temp = new ArrayList<>();
        Date currentDate = new Date();
        for (Booking booking : bookingList) {
            Userres user=new Userres(booking.getUserID());
            TempBooking tempBooking=new TempBooking(booking.getBookingID(),booking.getDate(),booking.getStartTime(),booking.getEndTime(),booking.getPurpose(),user);
            temp.add(tempBooking);
        }
        return temp;
    }
}

