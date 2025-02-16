package com.example.Dormly.service;

import com.example.Dormly.aws.PreSignedUrlService;
import com.example.Dormly.dto.CategoryDto;
import com.example.Dormly.dto.ListingDtoResponse;
import com.example.Dormly.entity.Category;
import com.example.Dormly.exceptions.CategoryNotFoundException;
import com.example.Dormly.exceptions.ListingNotFoundException;
import com.example.Dormly.repository.CategoryRepository;
import com.example.Dormly.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ListingRepository listingRepository;
    private final PreSignedUrlService preSignedUrlService;

    public List<ListingDtoResponse> findCategoriesByName(String name) {
        if(name==null || name.isEmpty()){
            throw new CategoryNotFoundException("Category name cannot be null or empty");
        }
         Category category = categoryRepository.findByName(name)
                 .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        List<ListingDtoResponse> listingDto = listingRepository.findAll()
                .stream()
                .filter(listing-> listing.getCategory().getName().equals(category.getName()))
                .map(ListingDtoResponse::DtoMapper)
                .toList();
        if(listingDto.isEmpty()){
            throw new ListingNotFoundException("There was no listing found for this category!");
        }

        /// we return the image Url, and profile Url(seller), allows us to render the image for each listing alongside seller profile information
        listingDto.forEach(listingDtoResponse->{
            listingDtoResponse.setListingUrl(preSignedUrlService.
                    generatePreSignedUrlListingById(listingDtoResponse.getListingId()));
            listingDtoResponse.setProfileUrl(preSignedUrlService.
                    getProfilePictureById(listingDtoResponse.getProfileId()));
        });

        return listingDto;

    }



}
