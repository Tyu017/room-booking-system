package com.example.demo.controller;

import com.example.demo.Response.Airor;
import com.example.demo.Response.UserDetailsResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class User_cont {

    private final UserRepository userRepository;

    public User_cont(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> getUserDetails(@RequestParam("userID") int userID) {
        Optional<User> optionalUser = userRepository.findById(userID);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDetailsResponse userDetails = new UserDetailsResponse(user.getName(), user.getUserID(), user.getEmail());
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Airor("User does not exist"));
        }
    }
}

