package com.example.Dormly.dto;

import com.example.Dormly.entity.Listing;
import com.example.Dormly.entity.Profile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import lombok.*;

import java.math.BigDecimal;
import java.net.URL;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class  ListingDtoResponse {
    @JsonIgnore //dont serialize this to the response
    private Long listingId;
    private String title;
    private BigDecimal price;
    private String description;
    private String brand;
    private String condition;
    //by default isSold in the listing entity is going to be No;
    private String location;
    private String category;
    private String availability;
    private URL ListingUrl;
    private Boolean isSold;
    /// these will be explicity set, as the listing object does not contain these properties
    private String firstname; //person who made the listing
    private String lastname;
    private URL profileUrl; //sellers profile picture
    @JsonIgnore
    private Long profileId;




    public static ListingDtoResponse DtoMapper(Listing listing){
       return ListingDtoResponse.builder()
               .listingId(listing.getId())
                .title(listing.getTitle())
                .price(listing.getPrice())
                .description(listing.getDescription())
                .brand(listing.getBrand())
                .condition(listing.getCondition())
                .location(listing.getLocation())
                .category(listing.getCategory())
                .availability(listing.getAvailability())
                .isSold(listing.isSold())
               .firstname(listing.getProfile().getUser().getFirstname())
               .lastname(listing.getProfile().getUser().getLastname())
               .profileId(listing.getProfile().getId())
               .build();

    }
}
