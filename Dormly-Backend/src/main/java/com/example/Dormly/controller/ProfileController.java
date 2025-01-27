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
@RequestMapping(path = "/api/v1/Dormly.com/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final JwtService jwtService;


    /**
     * the user sends a request, we extract the subject from the jwt token
     * and this way we can find the profile associated to the specific user making the request
     * @return ProfileDto - which is used to hide internals when sending back profile information
     *
     */

    @GetMapping("my-account")
    public ResponseEntity<ProfileDto> fetchUserProfile(HttpServletRequest request){
        String userEmail = jwtService.retrieveUserFromReq(request);
        System.out.println("the extracted email" +  userEmail);
       ProfileDto profileDto = profileService.fetchProfile(userEmail);
       return new ResponseEntity<>(profileDto, HttpStatus.OK);

    }
}
