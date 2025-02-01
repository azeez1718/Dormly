package com.example.Dormly.jwt.controller;

import com.example.Dormly.jwt.dto.AuthResponse;
import com.example.Dormly.jwt.dto.LoginDto;
import com.example.Dormly.jwt.dto.RegisterDto;
import com.example.Dormly.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Dormly.com/")
@Slf4j
public class AuthController {

    private final AuthService authService;



    @PostMapping(path="/Sign-up")
    public ResponseEntity<AuthResponse>SignUp(@RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(authService.giveToken(registerDto), HttpStatus.CREATED);
    }


    @PostMapping(path = "/login")
    public ResponseEntity<AuthResponse> Login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(authService.userLogin(loginDto));
    }

}
