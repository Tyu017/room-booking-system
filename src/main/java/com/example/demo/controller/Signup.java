package com.example.demo.controller;

import com.example.demo.Requests.SignupRequest;
import com.example.demo.Response.Airor;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class Signup {

    @Autowired
    private final UserRepository userRepository;

    public Signup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody SignupRequest userRequest) {
        Optional<User> existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser.isPresent()) {
//            ErrorResponse errorResponse=new E
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Airor("Forbidden, Account already exists"));
        }
        else {
            User user = new User();
            user.setEmail(userRequest.getEmail());
            user.setName(userRequest.getName());
            user.setPassword(userRequest.getPassword());
            userRepository.save(user);
            return ResponseEntity.ok("Account Creation Successful");
        }
    }
}


