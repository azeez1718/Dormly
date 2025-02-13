package com.example.Dormly.dto;

import com.example.Dormly.entity.Listing;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingDtoRequest {

    private String title;
    private BigDecimal price;
    private String description;
    private String brand;
    private String condition;
    //by default  visibility is set to true
    private String location;
    private String category;
    private String availability;

    public static void updateListing(Listing listing, ListingDtoRequest dto) {
        listing.setTitle(dto.getTitle());
        listing.setPrice(dto.getPrice());
        listing.setDescription(listing.getDescription());
        listing.setBrand(dto.getBrand());
        listing.setCondition(dto.getCondition());
        listing.setLocation(dto.getLocation());
        listing.setAvailability(dto.getAvailability());
        listing.setUpdated_at(LocalDate.now());

    }

}
