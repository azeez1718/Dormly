package com.example.Dormly.dto;

import com.example.Dormly.entity.Listing;
import com.example.Dormly.entity.Profile;
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
    private String title;
    private BigDecimal price;
    private String description;
    private String brand;
    private String condition;
    //by default isSold in the listing entity is going to be No;
    private String location;
    private String category;
    private String availability;
    private URL imageUrl;
    private Boolean isSold;
    private Profile profile;


    public static ListingDtoResponse DtoMapper(Listing listing){
       return ListingDtoResponse.builder()
                .title(listing.getTitle())
                .price(listing.getPrice())
                .description(listing.getDescription())
                .brand(listing.getBrand())
                .condition(listing.getCondition())
                .location(listing.getLocation())
                .category(listing.getCategory())
                .availability(listing.getAvailability())
                .isSold(listing.isSold())
                .profile(listing.getProfile())
               .build();

    }
}
