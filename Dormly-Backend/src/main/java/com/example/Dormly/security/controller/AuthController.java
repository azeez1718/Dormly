package com.example.Dormly.security.controller;

import com.example.Dormly.security.dto.RegisterDto;
import com.example.Dormly.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/Dormly")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path="Sign-up")
    public ResponseEntity<String>SignUp(@RequestBody RegisterDto registerDto){
        String token = authService.giveToken(registerDto);
        return ResponseEntity.ok(token);
    }

}
