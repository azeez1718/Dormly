package com.example.Dormly.aws;


import com.example.Dormly.entity.Listing;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.exceptions.ListingNotFoundException;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.repository.ListingRepository;
import com.example.Dormly.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreSignedUrlService {

    private final ListingRepository listingRepository;
    private final S3Service s3Service;
    private final ProfileRepository profileRepository;

    @Value("${aws.bucket.profiles}")
    private String profileBucket;

    @Value("${aws.bucket.listing}")
    private String bucketName;


    /**
     * to prevent circular dependencies,
     * this shared service allows us to fetch presigned urls for both profiles and listings
     */


    public URL generatePreSignedUrlListingById(Long id){
        Listing retreiveListing = listingRepository.findById(id)
                .orElseThrow(()-> new ListingNotFoundException("Listing not found"));

        if(retreiveListing.getListingImageURL() == null){
            throw new ListingNotFoundException("Listing image not found");
        }
        String imageUrl = retreiveListing.getListingImageURL();
        /// were also going to need the profile id of the person who made each listing as that is in our key
        Long profileId = retreiveListing.getProfile().getId();

        String key = "uploads/listings/%s/%s".formatted(profileId, imageUrl);
        return s3Service.generatePreSignedUrls(bucketName, key);


    }



    /// this function is for the specific user who wants to see his account when he logs in
    public URL generateProfilePreSignedUrlByEmail(String userEmail) {
        Profile userProfile = profileRepository.findByEmail(userEmail).
                orElseThrow(() -> new ProfileNotFoundException("user with email " +
                        userEmail + " does not exist"));

        Long profileId = userProfile.getId();
        String profileImageId = userProfile.getProfilePictureId();


        //ensure an empty key path to s3 isnt being sent
        if (profileImageId.isEmpty()) {
            throw new ProfileNotFoundException("profile with id : " + profileId + " does not have a profile picture");

        }
        //the key is the unique identifier so the location of the path, which was created using Profileid and generated image id
        //the profile image Id automatically includes the extension
        String key = "uploads/profile/%s/%s".formatted(profileId, profileImageId);

        return s3Service.generatePreSignedUrls(profileBucket, key);

    }

    /**
     * used in the Listing service to fetch the profile id when fetching all listings
     * we may need the profile id to display the seller of a listing when a buyer clicks on the product
     * @param id - the profile id related to the listing
     * @return -we return a url thats being set as the profileImageUrl in the listing dto return
     */
    public URL getProfilePictureById(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(() ->
                new ProfileNotFoundException("profile with id " + id + " does not exist"));

        Long profileId = profile.getId();
        String profilePictureId = profile.getProfilePictureId();
        String key = "uploads/profile/%s/%s".formatted(profileId, profilePictureId);

        return s3Service.generatePreSignedUrls(profileBucket, key);


    }


        /**
         * Used when the user goes to their profile, and an api call is made to render profile information
         * we call this function in the fetch profile function in the profile service
         * we pass the profile id, and view his listings, for each listing id, we call the generateUrl method
         * this for each user Listing will generate a presigned URL based on the bucket and the key defined in the db
         * @param profileId - this is the unique identifier for each user
         * @return a list of URLS
         */
        public List<URL> findListingsByProfile(Long profileId){
            //TODO do a direct db lookup instead of iterating through the listings
            return listingRepository.findAll()
                    .stream()
                    .filter(listing -> listing.getProfile().getId().equals(profileId))
                    .map(listing -> generatePreSignedUrlListingById(listing.getId()))
                    .collect(Collectors.toList());

        }


        //TODO when a user wants to update their profile picture or image of their listing we delete them from s3

    }
