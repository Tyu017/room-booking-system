package com.example.demo.Requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Setter
@Getter
public class BookingRequest {
    private int userID;
    private int roomID;
    private Date dateOfBooking;
    private String timeFrom;
    private String timeTo;
    private String purpose;

}

