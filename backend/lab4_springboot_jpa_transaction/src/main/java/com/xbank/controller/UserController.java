package com.xbank.controller;

import com.xbank.domain.User;
import com.xbank.repository.UserRepository;
import com.xbank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/api/users/{fullName}")
    public ResponseEntity<User> updateFullNameReadOnly(@PathVariable(value = "fullName") String fullName) {
        try {
            return new ResponseEntity<>(userService.updateFullNameReadOnly(fullName), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
    }

    @GetMapping("/api/users/{fullName}/rollback")
    public ResponseEntity<User> updateFullNameRollback(@PathVariable(value = "fullName") String fullName) {
        try {
            return new ResponseEntity<>(userService.updateFullNameRollback(fullName), HttpStatus.CREATED);
        } catch (Exception e) {
            User user = userRepository.searchUserByFullName(fullName);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
}
