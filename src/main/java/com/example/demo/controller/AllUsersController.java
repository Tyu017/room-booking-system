package com.example.demo.controller;

import com.example.demo.Response.AllUserResponse;
import com.example.demo.Response.UserDetailsResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AllUsersController {

    private final UserRepository userRepository;

    public AllUsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<AllUserResponse> userDetailsList = fromUserList(users);
        return ResponseEntity.ok(userDetailsList);
    }

    public static List<AllUserResponse> fromUserList(List<User> userList) {
        return userList.stream()
                .map(user -> new AllUserResponse(user.getName(), user.getUserID(), user.getEmail()))
                .collect(Collectors.toList());
    }
}

