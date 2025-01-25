package com.example.Dormly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/Dormly.com")
public class TestController {


    @GetMapping(path = "/dashboard")
    public ResponseEntity<String> tester(){
        return ResponseEntity.ok("wow you have access to the protected endpoint!");
    }




}
