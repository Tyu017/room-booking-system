package com.example.demo.Response;

import com.example.demo.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDetailsResponse {
    private String name;
    private int userID;
    private String email;

    public UserDetailsResponse(String name, int userID, String email) {
        this.name = name;
        this.userID = userID;
        this.email = email;
    }
    public static List<UserDetailsResponse> fromUserList(List<User> userList) {
        return userList.stream()
                .map(user -> new UserDetailsResponse(user.getName(), user.getUserID(), user.getEmail()))
                .collect(Collectors.toList());
    }
}

