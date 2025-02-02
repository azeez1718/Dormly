package com.example.Dormly.controller;


import com.example.Dormly.dto.ListingDtoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/Dormly/listing")
public class ListingController {

    private final listingService ListingService;

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void>createListing(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestPart("listing")ListingDtoRequest listingDtoRequest,
                                             @RequestPart("file") MultipartFile file) {

        String userEmail = userDetails.getUsername();
        return new ResponseEntity<>(listingService.createListing(userEmail, listingDtoRequest, file),
                HttpStatus.CREATED);

    }
}
