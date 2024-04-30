package com.example.demo.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TempBooking {
    @JsonProperty("bookingID")
    private int bookingId;

    @JsonProperty("dateOfBooking")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bookingDate;

    @JsonProperty("timeFrom")
    private String timeFrom;

    @JsonProperty("timeTo")
    private String timeTo;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("user")
    private Userres user;

    public TempBooking(int bookingId, Date bookingDate, String timeFrom, String timeTo, String purpose, Userres user) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.purpose = purpose;
        this.user = user;
    }

    // Getters and setters
}
