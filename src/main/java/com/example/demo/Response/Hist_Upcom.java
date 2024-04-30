package com.example.demo.Response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

public class Hist_Upcom {

    private  String room ;
    private  int roomID ;
    private  int bookingID ;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBooking ;
    private  String timeFrom ;
    private  String timeTo;
    private  String purpose;

    public Hist_Upcom(String room, int roomID, int bookingID, Date dateOfBooking, String timeFrom, String timeTo, String purpose){
        this.room = room;
        this.roomID = roomID;
        this.bookingID = bookingID;
        this.dateOfBooking = dateOfBooking;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.purpose = purpose;
    }
}
