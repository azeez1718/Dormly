package com.example.Dormly.controller;

import com.example.Dormly.dto.ProfileDto;
import com.example.Dormly.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("api/v1/Dormly.com/profile")
@RestController
public class ProfileController {

    private final ProfileService profileService;
    /**
     * the user sends a request, the filters validate the token
     * we set an authentication object of the user before the dispatcher servlet delegates request to the controller
     * @return ProfileDto - which is used to hide internals when sending back profile information
     *
     */
    @GetMapping(path = "my-account")
    public ResponseEntity<ProfileDto> fetchUserProfile(@AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(profileService.fetchProfile(userDetails.getUsername()), HttpStatus.OK);

    }

    //allow users to upload images to our s3 bucket
    //profile pictures are se
    @PostMapping(path = "upload-photo")
    public ResponseEntity<Void> uploadProfilePicture(@AuthenticationPrincipal )
}
