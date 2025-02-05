package com.example.Dormly.service;

import com.example.Dormly.aws.S3Service;
import com.example.Dormly.dto.ProfileDto;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Value("${aws.bucket.profiles}")
    private String profileBucket;
    private final ProfileRepository profileRepository;
    private final S3Service s3Service;


    public ProfileDto fetchProfile(String userEmail) {
        Optional<Profile> userProfile = profileRepository.findByEmail(userEmail);

        if (userProfile.isEmpty()) {
            throw new ProfileNotFoundException("This user does not exist");
        }
        //if the user has a valid profile, we can then also get their email and firstname to return back to the client

        Profile profile = userProfile.get();

        //return the image URL from the presigned url we generate for the user, and set it in DTO to return to client
        URL imageUrl = generatePreSignedUrl(userEmail);


        /**
         * convert the profile object we return to a DTO to hide internals
         */
        ProfileDto profileDto = new ProfileDto(
                imageUrl,
                profile.getBio(),
                profile.getLocation(),
                profile.getUser().getEmail(),
                profile.getUser().getFirstname(),
                profile.getUser().getLastname()

        );

        return profileDto;

    }

    /// this function is for the specific user who wants to see his account when he logs in
    public URL generatePreSignedUrl(String userEmail) {
        Profile userProfile = profileRepository.findByEmail(userEmail).
                orElseThrow(() -> new ProfileNotFoundException("user with email " +
                        userEmail + " does not exist"));

        Long profileId = userProfile.getId();
        String profileImageId = userProfile.getProfilePictureId();


        //ensure an empty key path to s3 isnt being sent
        if (profileImageId.isEmpty()) {
            //TODO: add exception with more clarity
            throw new ProfileNotFoundException("profile with id : " + profileId + " does not have a profile picture");

        }
        //the key is the unique identifier so the location of the path, which was created using Profileid and generated image id
        //the profile image Id automatically includes the extension
        String key = "uploads/profile/%s/%s".formatted(profileId, profileImageId);

        return s3Service.generatePreSignedUrls(profileBucket, key);

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


    public URL getProfilePicture(String userEmail){
        //will find the profile of the user
        //we will pass the key and the bucket and call the presigned url function
        //this returns a temporary signed URL allowing users to access our bucket whilst hiding credentials
        return generatePreSignedUrl(userEmail);
    }


    public URL getProfilePictureById(Long id){
        Profile profile = profileRepository.findById(id).orElseThrow(()->
                new ProfileNotFoundException("profile with id " + id + " does not exist"));

        Long profileId = profile.getId();
        String profilePictureId = profile.getProfilePictureId();
        String key = "uploads/profile/%s/%s".formatted(profileId, profilePictureId);

        return s3Service.generatePreSignedUrls(profileBucket, key);



    }



}
