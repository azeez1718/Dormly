package com.example.Dormly.security.service;

import com.example.Dormly.security.dto.RegisterDto;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    public String giveToken(RegisterDto registerDto) {

    }
}
