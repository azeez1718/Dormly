package com.example.Dormly.security.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/Dormly.com")
public class TestController {

    @GetMapping(path = "test")
    public String hello(){
        return "hello";
    }


}
