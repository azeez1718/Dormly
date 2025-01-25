package com.example.Dormly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/v1/Dormly.com")
public class TestController {


    @GetMapping(path = "/dashboard")
    public ResponseEntity<Date> tester(){
        Date date = new Date();
        return ResponseEntity.ok(date);
    }




}
