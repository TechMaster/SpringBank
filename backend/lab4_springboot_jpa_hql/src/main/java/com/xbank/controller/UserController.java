package com.xbank.controller;

import com.xbank.domain.User;
import com.xbank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users/{fullName}")
    public ResponseEntity<User> getUserByFullName(@PathVariable(value = "fullName") String fullName) {
        try {
            return new ResponseEntity<>(userService.getUserByFullName(fullName), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
    }
}
