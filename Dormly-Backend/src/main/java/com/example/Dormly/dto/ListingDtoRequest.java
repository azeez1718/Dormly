package com.example.Dormly.dto;

import lombok.*;

import java.math.BigDecimal;

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

}
