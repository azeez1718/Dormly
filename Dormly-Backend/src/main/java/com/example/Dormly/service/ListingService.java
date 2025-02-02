package com.example.Dormly.service;

import com.example.Dormly.aws.S3Service;
import com.example.Dormly.dto.ListingDtoRequest;
import com.example.Dormly.entity.Listing;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.repository.ListingRepository;
import com.example.Dormly.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ListingService {
    private final ProfileRepository profileRepository;
    private final ListingRepository listingRepository;
    private final S3Service s3Service;

    @Value("${aws.buckets.listings}")
    private String bucketName;

    @Transactional
    public void createListing(String userEmail, ListingDtoRequest listingDtoRequest, MultipartFile multipartFile) throws IOException {
        //fetch the profile making that is trying to make a listing
        Profile profile = profileRepository.findByEmail(userEmail).
                orElseThrow(()->new ProfileNotFoundException(userEmail));

        /**
         * to store the listing in our aws bucket we need to define the key, which will be the profile id
         * & a random unique UUID which will be stored as the Listing URL
         */

        Long profileId = profile.getId();
        String fileUUID = UUID.randomUUID().toString();
        byte [] file = multipartFile.getBytes();
        String key = "uploads/listings/%s/%s".formatted(profileId,fileUUID);

        s3Service.putObject(bucketName, key, file);


        //convert the dto to a listing object using Builder
        Listing listing = Listing.builder()
                .brand(listingDtoRequest.getBrand())
                .availability(listingDtoRequest.getAvailability())
                .condition(listingDtoRequest.getCondition())
                .category(listingDtoRequest.getCategory())
                .Location(listingDtoRequest.getLocation())
                .title(listingDtoRequest.getTitle())
                .price(listingDtoRequest.getPrice())
                .build();

        //we can set the values to save the entity, these will be set default in the backend
        listing.setListedDate(new Date());
        listing.setUpdatedAt(new Date());
        listing.setProfile(profile);

        //set the fileUUID to the image url so we can fetch it when generating a presigned url
        listing.setListingImageURL(fileUUID);

        listingRepository.save(listing);

    }
}
