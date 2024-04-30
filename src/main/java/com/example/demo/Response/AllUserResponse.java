package com.example.demo.Response;

import com.example.demo.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AllUserResponse {
    private int userID;
    private String name;
    private String email;

    public AllUserResponse(String name, int userID, String email) {
        this.userID = userID;
        this.name = name;
        this.email = email;
    }

}

