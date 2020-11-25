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

    @GetMapping("/api/users/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable(value = "name") String name) {
        try {
            return new ResponseEntity<>(userService.getUserByFullName(name), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
    }

    @GetMapping("/api/users/{name}/entityManager")
    public ResponseEntity<User> getUserByNameEntityManager(@PathVariable(value = "name") String name) {
        try {
            return new ResponseEntity<>(userService.getUserByFullNameWithEntityManager(name), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
    }
}
