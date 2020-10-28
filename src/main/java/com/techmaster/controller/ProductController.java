package com.techmaster.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class ProductController {

    @GetMapping(path = "/products")
    public ResponseEntity<String> getProducts(){
        return new ResponseEntity<>("getProducts", HttpStatus.OK);
    }

    @GetMapping(path = "/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("logout", HttpStatus.OK);
    }
}