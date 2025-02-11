package com.example.Dormly.service;

import com.example.Dormly.aws.PreSignedUrlService;
import com.example.Dormly.aws.S3Service;
import com.example.Dormly.dto.ListingDtoResponse;
import com.example.Dormly.dto.ProfileDto;
import com.example.Dormly.entity.Listing;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Value("${aws.bucket.profiles}")
    private String profileBucket;
    private final ProfileRepository profileRepository;
    private final S3Service s3Service;
    private final PreSignedUrlService preSignedUrlService;

    public ProfileDto fetchProfile(String userEmail) {
        Optional<Profile> userProfile = profileRepository.findByEmail(userEmail);

        if (userProfile.isEmpty()) {
            throw new ProfileNotFoundException("This user does not exist");
        }
        //if the user has a valid profile, we can then also get their email and firstname to return back to the client

        Profile profile = userProfile.get();

        //return the image URL from the presigned url we generate for the user, and set it in DTO to return to client
        URL profileUrl = preSignedUrlService.generateProfilePreSignedUrlByEmail(userEmail);

        List<ListingDtoResponse> listingDto = profile.getListings()
                .stream()
                .map(ListingDtoResponse::DtoMapper)
                .toList();

        /// because atm each user can only upload a single image, we can set the image URL by calling -
        /// the generatePresignedURLById and return the listing image, so it is directly associated to that listing
        /// because foreach is terminal and returns a void, we can just set the Url of the dto

        listingDto.forEach(dto->dto.setListingUrl(
                preSignedUrlService.generatePreSignedUrlListingById(dto.getListingId())));






        /**
         * convert the profile object we return to a DTO to hide internals
         */

        return ProfileDto.builder()
                .id(profile.getId())
                .image(profileUrl)
                .bio(profile.getBio())
                .email(profile.getUser().getEmail())
                .firstname(profile.getUser().getFirstname())
                .lastname(profile.getUser().getLastname())
                .location(profile.getLocation())
                .profileListings(listingDto)
                .build();

    }






    public void uploadProfilePicture(String userEmail, MultipartFile multipartFile) {
        /**
         * Ensure the User making this request exists
         * Make an API call to s3 to put the object in our specified bucket
         */
        //we need to retrieve the profile to set the unique identifier within the key to the profile Id
        Profile userProfile = profileRepository.findByEmail(userEmail).
                orElseThrow(() -> new ProfileNotFoundException("user with email " + userEmail + " does not exist"));

        Long profileId = userProfile.getId();
        String profilePictureId = UUID.randomUUID().toString();
        // Infer the file extension
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            throw new IllegalArgumentException("Invalid file format");
        }

        try {
            byte[] file = multipartFile.getBytes();
            //api request to store the object into our aws bucket based on our credentials defined in the s3 client
            s3Service.putObject(
                    profileBucket,
                    "uploads/profile/%s/%s%s".formatted(profileId, profilePictureId, extension),
                    file);
        } catch (IOException e) {
            //TODO add custom exceptions
            throw new RuntimeException("unable to upload file", e);

        }
        //save the Users image Id, so that we can always retrieve it -image.extension
        userProfile.setProfilePictureId(profilePictureId + extension);
        profileRepository.save(userProfile);


        /**
         * Aws structure - the Key represents the unique file that identifies our object
         * Bucket
         *      uploads
         *          profile
         *              1  ->profile id
         *                      22 -> the random number
         *                      32
         *
         */


    }



    /**
     * called when the user aims to view their profile pic in the homepage, called on NgOnInit
     * return the url from s3 and placed in our ui as a small icon
     * @param userEmail - the user that is rendering the homepage
     * @return URL
     */
    public URL getProfilePicture(String userEmail){
        //will find the profile of the user
        //we will pass the key and the bucket and call the presigned url function
        //this returns a temporary signed URL allowing users to access our bucket whilst hiding credentials
        return preSignedUrlService.generateProfilePreSignedUrlByEmail(userEmail);
    }









}
