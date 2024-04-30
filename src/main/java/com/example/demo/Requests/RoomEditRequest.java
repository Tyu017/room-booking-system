package com.example.demo.Requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoomEditRequest {
    private int roomID;
    private String roomName;
    private int roomCapacity;
}
