package com.example.Dormly.controller;


import com.example.Dormly.dto.ListingDtoRequest;
import com.example.Dormly.dto.ListingDtoResponse;
import com.example.Dormly.entity.Listing;
import com.example.Dormly.service.ListingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequestMapping(value = "api/v1/Dormly.com/listing")
@RestController
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void>createListing(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestPart("listing")ListingDtoRequest listingDtoRequest,
                                             @RequestPart("file") MultipartFile file) throws IOException {

        String userEmail = userDetails.getUsername();
        log.info("listing dto{}", listingDtoRequest.toString());
        log.info("{}----------------------------------------", file.getOriginalFilename());
        listingService.createListing(userEmail, listingDtoRequest, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);

    }


    @GetMapping(path = "")
    public ResponseEntity<ListingDtoResponse>findAllListings(@AuthenticationPrincipal UserDetails userDetails){
        String userEmail = userDetails.getUsername();

    }
}
