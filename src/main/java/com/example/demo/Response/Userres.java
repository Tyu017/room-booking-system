package com.example.demo.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Userres {

    @JsonProperty("userID")
    private int userID;

    public Userres(int userID) {
        this.userID = userID;
    }
}
