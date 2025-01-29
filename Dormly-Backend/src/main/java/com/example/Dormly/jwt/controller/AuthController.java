package com.example.Dormly.jwt.controller;

import com.example.Dormly.jwt.dto.AuthResponse;
import com.example.Dormly.jwt.dto.LoginDto;
import com.example.Dormly.jwt.dto.RegisterDto;
import com.example.Dormly.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/Dormly.com/")
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
