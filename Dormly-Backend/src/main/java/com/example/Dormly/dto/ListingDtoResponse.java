package com.example.Dormly.dto;

import com.example.Dormly.entity.Profile;

import java.math.BigDecimal;
import java.net.URL;

public class ListingDtoResponse {
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
}
