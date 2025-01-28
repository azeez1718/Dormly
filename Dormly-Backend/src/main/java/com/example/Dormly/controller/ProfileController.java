package com.example.Dormly.controller;

import com.example.Dormly.dto.ProfileDto;
import com.example.Dormly.security.service.JwtService;
import com.example.Dormly.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/Dormly.com/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final JwtService jwtService;


    @GetMapping(path = "my-account")
    public ResponseEntity<ProfileDto> fetchUserProfile(HttpServletRequest Request){
        String userEmail = jwtService.retrieveUserFromRequest(Request);
        return new ResponseEntity<>(profileService.fetchProfile(userEmail), HttpStatus.OK);

    }
}
