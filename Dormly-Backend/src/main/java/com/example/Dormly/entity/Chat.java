package com.example.Dormly.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Chat {
    /// the output message acts as our DTO

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Profile seller;


    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Profile buyer;

    @Column(nullable = false)
    private String content;

    /// we can persist the listing id as a foreign key
    /// helps with data intergirty and makes it easier to query the listings

    @ManyToOne
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;


}
