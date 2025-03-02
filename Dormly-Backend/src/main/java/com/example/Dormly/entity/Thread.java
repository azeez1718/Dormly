package com.example.Dormly.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Thread {
    ///This thread represent the distinct conversation between two users

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Profile seller;


    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Profile buyer;

    /// we can persist the listing id as a foreign key
    /// helps with data intergirty and makes it easier to query the listings
    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;




}
