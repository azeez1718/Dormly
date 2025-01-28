package com.example.Dormly.controller;

import com.example.Dormly.dto.ProfileDto;
import com.example.Dormly.security.service.JwtService;
import com.example.Dormly.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/Dormly.com/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final JwtService jwtService;

    /**
     * the user sends a request, the filters validate the token
     * we set an authentication object of the user before the dispatcher servlet delegates request to the controller
     * @return ProfileDto - which is used to hide internals when sending back profile information
     *
     */
    @GetMapping(path = "my-account")
    public ResponseEntity<ProfileDto> fetchUserProfile(@AuthenticationPrincipal UserDetails userDetails){;
        return new ResponseEntity<>(profileService.fetchProfile(userDetails.getUsername()), HttpStatus.OK);

    }
}
