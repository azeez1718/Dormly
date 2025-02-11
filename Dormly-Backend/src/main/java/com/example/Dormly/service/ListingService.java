package com.example.Dormly.service;

import com.example.Dormly.aws.PreSignedUrlService;
import com.example.Dormly.aws.S3Service;
import com.example.Dormly.dto.CategoryDto;
import com.example.Dormly.dto.ListingDtoRequest;
import com.example.Dormly.dto.ListingDtoResponse;
import com.example.Dormly.entity.Category;
import com.example.Dormly.entity.Listing;
import com.example.Dormly.entity.Profile;
import com.example.Dormly.exceptions.CategoryNotFoundException;
import com.example.Dormly.exceptions.ListingNotFoundException;
import com.example.Dormly.exceptions.ProfileNotFoundException;
import com.example.Dormly.repository.CategoryRepository;
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
    private final PreSignedUrlService preSignedUrlService;
    private final CategoryRepository categoryRepository;

    @Value("${aws.bucket.listing}")
    private String bucketName;

    @Transactional
    public ListingDtoResponse createListing(String userEmail, ListingDtoRequest listingDtoRequest, MultipartFile multipartFile) throws IOException {
        //fetch the profile making that is trying to make a listing
        Profile profile = profileRepository.findByEmail(userEmail).
                orElseThrow(()->new ProfileNotFoundException(userEmail));

        Category category = categoryRepository.findByName(listingDtoRequest.getCategory())
                .orElseThrow(()-> new  CategoryNotFoundException("There was not a valid category included in the request"));


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
                    .category(category)
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
            ///  returning listing info back to client as this is used to render the listing confirmation ui
            return creationResponse(listing);

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

        if(listingDto.isEmpty()){
            throw new ListingNotFoundException("Listings not found");
        }

        /**
         * so we first set the Listing presigned url to return when returning all listed items to the user
         *we then for each dto need to return a pre-signed url of the profile picture,
         * to see which user made the listing
         */
        listingDto.forEach(dto-> {
            dto.setListingUrl(preSignedUrlService.generatePreSignedUrlListingById(dto.getListingId()));
            dto.setProfileUrl(preSignedUrlService.getProfilePictureById(dto.getProfileId()));
        });

        return listingDto;


    }



    public ListingDtoResponse creationResponse(Listing listing){
       return ListingDtoResponse.builder()
                .listingId(listing.getId())
               .firstname(listing.getProfile().getUser().getFirstname())
                .title(listing.getTitle())
                .description(listing.getDescription())
                .price(listing.getPrice())
                .ListingUrl(preSignedUrlService.generatePreSignedUrlListingById(listing.getId()))
                .createdDate(listing.getListedDate())
               .location(listing.getLocation())
                .build();

    }


    /**
     *
     * @param id - binded to the path variable, when a user clicks on the card instance, we make an api call
     * This call fetches the listing information for a single card when a user is about to buy
     * @return
     */
    public ListingDtoResponse findListingById(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(()-> new ListingNotFoundException("Listing not found"));

        try {
            //convert listing to a DTO, to return all relevant information that's to be displayed in the frontend
            ListingDtoResponse dto = ListingDtoResponse.DtoMapper(listing);
            dto.setListingUrl(preSignedUrlService.generatePreSignedUrlListingById(listing.getId()));
            dto.setProfileUrl(preSignedUrlService.getProfilePictureById(listing.getProfile().getId()));
            return dto;

        }catch(Exception e){
            throw new RuntimeException("Error whilst retrieving listing", e);
        }
    }


    public List<CategoryDto> findAllCategories() {

        List<CategoryDto> categoryDtos = categoryRepository.findAll()
                .stream()
                .map(CategoryDto::fromCategory)
                .toList();

        if(categoryDtos.isEmpty()){
            throw new CategoryNotFoundException("Category not found");
        }
        return categoryDtos;
    }
}
