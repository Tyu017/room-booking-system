package com.example.demo.Requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BookingeditRequest {

    private int userID;
    private int roomID;
    private int bookingID;
    private Date dateOfBooking;
    private String timeFrom;
    private String timeTo;
    private String purpose;
}
