package com.example.Dormly.service;

import com.example.Dormly.aws.S3Service;
import com.example.Dormly.dto.ListingDtoRequest;
import com.example.Dormly.dto.ListingDtoResponse;
import com.example.Dormly.entity.Listing;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.exceptions.ListingNotFoundException;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.repository.ListingRepository;
import com.example.Dormly.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ListingService {
    private final ProfileRepository profileRepository;
    private final ListingRepository listingRepository;
    private final S3Service s3Service;
    private final ProfileService profileService;

    @Value("${aws.bucket.listing}")
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

        try {
            byte[] file = multipartFile.getBytes();
            //get the extension of the image

            //ensure file name isn't null
            if(multipartFile.getOriginalFilename() == null){
                throw new FileNotFoundException("File not found");
            }
            String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            String key = "uploads/listings/%s/%s".formatted(profileId, fileUUID);
            s3Service.putObject(bucketName, key + extension, file);


            //convert the dto to a listing object using Builder
            Listing listing = Listing.builder()
                    .brand(listingDtoRequest.getBrand())
                    .availability(listingDtoRequest.getAvailability())
                    .condition(listingDtoRequest.getCondition())
                    .category(listingDtoRequest.getCategory())
                    .location(listingDtoRequest.getLocation())
                    .title(listingDtoRequest.getTitle())
                    .price(listingDtoRequest.getPrice())
                    .description(listingDtoRequest.getDescription())
                    .build();

            //we can set the values to save the entity, these will be set default in the backend
            listing.setListedDate(LocalDate.now());
            listing.setUpdated_at(LocalDate.now());
            listing.setProfile(profile);
            listing.setSold(false);
            //set the fileUUID to the image url with extension so we can fetch it when generating a presigned url
            listing.setListingImageURL(fileUUID + extension);

            listingRepository.save(listing);
        }catch(Exception e){
            throw new RuntimeException("Error whilst creating listing", e);
        }

    }

    public List<ListingDtoResponse> findAllListings() {
        /**
         *  we'll need to iterate through each listing items and convert them into a listingDTO
         *  because Map returns a new object we'll first convert it to a dto then set the presigned url using peek
         * peek works but it isn't the best solution to use
         */

        List<ListingDtoResponse> listingDto = listingRepository.findAll()
                .stream()
                .map(ListingDtoResponse::DtoMapper)
                .toList();

        /**
         * so we first set the Listing presigned url to return when returning all listed items to the user
         *we then for each dto need to return a pre-signed url of the profile picture,
         * to see which user made the listing
         */
        listingDto.forEach(dto-> {
            dto.setListingUrl(generatePreSignedUrlListing(dto.getListingId()));
            dto.setProfileUrl(profileService.getProfilePictureById(dto.getProfileId()));
        });

        return listingDto;


    }

    public URL generatePreSignedUrlListing(Long id){
        Listing retreiveListing = listingRepository.findById(id)
                .orElseThrow(()-> new ListingNotFoundException("Listing not found"));

        String imageUrl = retreiveListing.getListingImageURL();
        /// were also going to need the profile id of the person who made each listing as that is in our key
        Long profileId = retreiveListing.getProfile().getId();

        String key = "uploads/listings/%s/%s".formatted(profileId, imageUrl);
        return s3Service.generatePreSignedUrls(bucketName, key);


    }



}
