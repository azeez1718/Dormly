package com.example.Dormly.controller;

import com.example.Dormly.dto.ProfileDto;
import com.example.Dormly.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;


@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/Dormly/profile")
@RestController
public class ProfileController {

    private final ProfileService profileService;
    /**
     * the user sends a request, the filters validate the token
     * we set an authentication object of the user before the dispatcher servlet delegates request to the controller
     * @return ProfileDto - which is used to hide internals when sending back profile information
     *The presigned Url that the user uses to view their profile picture is sent as a url alongside the JSON
     * this is more optimal than sending 2 separate responses one being a byte and the other a json
     */
    @GetMapping(path = "my-account")
    public ResponseEntity<ProfileDto> fetchUserProfile(@AuthenticationPrincipal UserDetails userDetails){
        return new ResponseEntity<>(profileService.fetchProfile(userDetails.getUsername()), HttpStatus.OK);
    }

    //allow users to upload images to our s3 bucket
    //profile pictures are sent to the profile bucket

    /**
     *
     * @param userDetails - represents the currently authenticated user
     * @param multipartFile - client side will include the file in the params
     * @return - void, nothing to return to the user, however a status code to see successful request
     */
    @PostMapping(path = "upload-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadProfilePicture(@AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestPart("file")MultipartFile multipartFile){
        log.info("Uploading profile picture");
        String userEmail = userDetails.getUsername();
        profileService.uploadProfilePicture(userEmail, multipartFile);
        return new ResponseEntity<>(HttpStatus.CREATED);


    }

    /**
     * return the profile image of the user, which will be set in the homepage
     * @param userDetails
     * @return
     */
    @GetMapping(value = "home/profile-picture")
    public ResponseEntity<URL>fetchHomeProfile(@AuthenticationPrincipal UserDetails userDetails){
        String userEmail = userDetails.getUsername();
        return new ResponseEntity<>(profileService.getProfilePicture(userEmail), HttpStatus.OK);

    }





}
