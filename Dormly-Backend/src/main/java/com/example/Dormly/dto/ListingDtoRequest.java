package com.example.Dormly.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingDtoRequest {

    private String title;
    private int price;
    private String description;
    private String brand;
    private String condition;
    //by default isSold in the listing entity is going to be No;
    private String Location;
    private String category;
    private String availability;
}
