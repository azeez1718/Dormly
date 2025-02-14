package com.example.Dormly.controller;


import com.example.Dormly.dto.CategoryDto;
import com.example.Dormly.dto.ListingDtoRequest;
import com.example.Dormly.dto.ListingDtoResponse;
import com.example.Dormly.entity.Listing;
import com.example.Dormly.service.ListingService;
import jakarta.annotation.Nullable;
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
import java.util.List;

@Slf4j
@RequestMapping(value = "api/v1/Dormly/listing")
@RestController
public class ListingController {

    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }


    /**
     * Multipart form processes request by parts, it binds the values of the keys to the values in the method param
     * we set content type in the client side allow smooth deserialization(json->obj)
     * @param userDetails - currently authenticated user, we need to set the seller in our listing entity
     * @param listingDtoRequest - dto to hide internals-sent as a blob obj as we set the content type to json
     * @param file - the file the user uploads, we convert it to bytes before storing in aws
     * @return
     * @throws IOException
     */
    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ListingDtoResponse>createListing(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestPart("listing")ListingDtoRequest listingDtoRequest,
                                             @RequestPart("file") MultipartFile file) throws IOException {

        String userEmail = userDetails.getUsername();
        return ResponseEntity.status(HttpStatus.CREATED).body(listingService.createListing(userEmail,
                listingDtoRequest, file));

    }


    /**
     * we return all listings made in the application thus we do not need to use any individual user info
     * users make a request to see all the listed items in Dormly
     * there is another api that handles getting the individual listings for a user
     * @return
     */
    @GetMapping(path = "")
    public ResponseEntity<List<ListingDtoResponse>>findAllListings(){
        List<ListingDtoResponse> dtoResponse = listingService.findAllListings();
         return new ResponseEntity<>(dtoResponse, HttpStatus.OK);

    }


    @GetMapping(path = "/product/{id}")
    public ResponseEntity<ListingDtoResponse> findListingById(@PathVariable("id") Long id){
        ListingDtoResponse dtoResponse = listingService.findListingById(id);
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }


    @GetMapping(path = "/categories")
    public ResponseEntity<List<CategoryDto>> findAllCategories(){
        return new ResponseEntity<>(listingService.findAllCategories(), HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void>updateListing(@AuthenticationPrincipal UserDetails user,
                                                           @RequestPart("listing")ListingDtoRequest listingDtoRequest,
                                                           @Nullable @RequestPart("file") MultipartFile file,
                                                           @PathVariable("id")Long ListingId) throws IOException {
        listingService.updateListing(listingDtoRequest, user , file, ListingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *
     * @param user - ensure the user calling the api is the one who owns the listing
     * @param ListingId - unique identifier for the listing
     * @return no response in the body required
     */
    @DeleteMapping(path="/delete/{id}")
    public ResponseEntity<Void> deleteListing(@AuthenticationPrincipal UserDetails user,
                                              @PathVariable("id") Long ListingId){
        listingService.deleteListingById(user, ListingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
