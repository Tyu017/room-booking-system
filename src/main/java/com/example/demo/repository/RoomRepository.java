package com.example.demo.repository;

import com.example.demo.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RoomRepository extends JpaRepository<Room, Integer> {
    boolean existsByRoomname(String roomName);
    boolean existsByRoomID(Integer roomid);
    Room findByRoomID(int roomID);
    List<Room> findRoomByCapacityGreaterThanEqual(int capacity);
}

