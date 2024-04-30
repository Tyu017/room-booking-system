package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    private int userID;
    private int roomID;
    private String startTime;
    private String endTime;
    private Date date;
    private String purpose;

    public Booking() {}
    public Booking(String startTime, String endTime, int roomID, int userID, String purpose, Date date) {
        this.purpose = purpose;
        this.startTime = startTime;
        this.date = date;
        this.endTime = endTime;
        this.roomID = roomID;
        this.userID = userID;
    }


}
